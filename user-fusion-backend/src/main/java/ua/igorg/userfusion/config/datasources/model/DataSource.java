package ua.igorg.userfusion.config.datasources.model;

import lombok.Data;

/**
 * Created by igorg
 * on 01.06.2024
 */
@Data
public class DataSource {
    private DatabaseStrategy strategy; //: postgres
    private String url; //: jdbc://.....
    private String table; //: users
    private String user; //: testuser
    private String password; //: testpass
    private Mapping mapping;
}
