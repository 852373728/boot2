package com.sun.service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.sun.entity.PersistentLogins;
import com.sun.mapper.PersistentLoginsMapper;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.core.log.LogMessage;
import org.springframework.dao.DataAccessException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.dao.IncorrectResultSizeDataAccessException;
import org.springframework.security.web.authentication.rememberme.PersistentRememberMeToken;
import org.springframework.security.web.authentication.rememberme.PersistentTokenRepository;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Date;

/**
 * @date 2024/7/5
 */
@Service
public class MyPersistentTokenRepositoryService implements PersistentTokenRepository {

    private final Log logger = LogFactory.getLog(getClass());

    @Resource
    private PersistentLoginsMapper persistentLoginsMapper;


    @Override
    public void createNewToken(PersistentRememberMeToken token) {

        PersistentLogins persistentLogins = new PersistentLogins(token.getSeries(), token.getUsername(), token.getTokenValue(), token.getDate());
        persistentLoginsMapper.insert(persistentLogins);
    }

    @Override
    public void updateToken(String series, String tokenValue, Date lastUsed) {
        UpdateWrapper<PersistentLogins> updateWrapper = new UpdateWrapper<>();
        updateWrapper.set("token", tokenValue);
        updateWrapper.set("last_used", lastUsed);
        updateWrapper.eq("series", series);
        persistentLoginsMapper.update(updateWrapper);
    }

    @Override
    public PersistentRememberMeToken getTokenForSeries(String seriesId) {
        try {
            QueryWrapper<PersistentLogins> wrapper = new QueryWrapper<>();
            wrapper.eq("series", seriesId);
            PersistentLogins pl = persistentLoginsMapper.selectOne(wrapper);
            return new PersistentRememberMeToken(pl.getUsername(), pl.getSeries(), pl.getToken(), pl.getLastUsed());
        } catch (EmptyResultDataAccessException ex) {
            this.logger.debug(LogMessage.format("Querying token for series '%s' returned no results.", seriesId), ex);
        } catch (IncorrectResultSizeDataAccessException ex) {
            this.logger.error(LogMessage.format(
                    "Querying token for series '%s' returned more than one value. Series" + " should be unique",
                    seriesId));
        } catch (DataAccessException ex) {
            this.logger.error("Failed to load token for series " + seriesId, ex);
        }
        return null;
    }

    @Override
    public void removeUserTokens(String username) {
        QueryWrapper<PersistentLogins> wrapper = new QueryWrapper<>();
        wrapper.eq("username", username);
        persistentLoginsMapper.delete(wrapper);
    }
}
