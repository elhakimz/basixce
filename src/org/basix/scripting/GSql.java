package org.basix.scripting;

import groovy.sql.Sql;
import org.basix.util.AppConstant;

import java.sql.SQLException;

/**
 * @author Abiel
 *         Date: 4/5/13
 *         Time: 2:45 PM
 */
public class GSql {

    public static Sql getSql() throws SQLException, ClassNotFoundException {

            return Sql.newInstance(AppConstant.DB_URL, AppConstant.DB_USER,
                    AppConstant.DB_PASSWORD, AppConstant.DB_DRIVER);


    }

}
