package com.sun;

/**
 * @date 2024/5/23
 */
public abstract class Pae {

    public void qer(){
        System.out.println("父类方法执行");
    }


    public void rer(){
        System.out.println("父类的先行方法");

        cc();
    }

    public abstract void cc();
}
