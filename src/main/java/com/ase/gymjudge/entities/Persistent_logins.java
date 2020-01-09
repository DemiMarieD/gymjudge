package com.ase.gymjudge.entities;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.security.Timestamp;

//Needed for the logout
@Entity
@Table(name = "persistent_logins")
public class Persistent_logins {

    /*
    //run this for logout to work;
     DROP TABLE IF EXISTS `persistent_logins`;
        CREATE TABLE  `persistent_logins` (
          `username` varchar(64) NOT NULL,
          `series` varchar(64) NOT NULL,
          `token` varchar(64) NOT NULL,
          `last_used` timestamp NOT NULL default CURRENT_TIMESTAMP on update CURRENT_TIMESTAMP,
          PRIMARY KEY  (`series`)
      ) ENGINE=InnoDB DEFAULT CHARSET=utf8;
 */
    //seems to work like that..
    private String username;
    @Id
    private String series;
    private String token;
    private Timestamp last_used; //todo: default CURRENT_TIMESTAMP on update CURRENT_TIMESTAMP,


}
