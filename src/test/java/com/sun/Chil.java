package com.sun;

/**
 * @date 2024/5/23
 */
public class Chil extends Pae{


    public Chil() {
        qer();
    }

    @Override
    public void qer() {
        System.out.println("子类方法执行");
    }

    @Override
    public void cc() {
        System.out.println("子类后行");
    }
}
