package com.viewscenes.dao.pool;



class PoolProperty {

    public String m_sName;

    public String m_sDriver;

    public String m_sUrl;

    public String m_sUser;

    public String m_sPassword;

    public int m_nMinConn;

    public int m_nMaxConn;



    public PoolProperty(String name, String drivername, String url

                        , String user, String password, int minConn, int maxConn) {

        m_sName = name;

        m_sDriver = drivername;

        m_sUrl = url;

        m_sUser = user;

        m_sPassword = password;

        m_nMinConn = minConn;

        m_nMaxConn = maxConn;

    }

}
