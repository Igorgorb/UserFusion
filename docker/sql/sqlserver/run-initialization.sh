#!/bin/bash
# Wait to be sure that SQL Server came up
#sleep 120s
#
## Run the setup script to create the DB and the schema in the DB
## Note: make sure that your password matches what is in the Dockerfile
#/opt/mssql-tools/bin/sqlcmd -S localhost -U sa -P $MSSQL_SA_PASSWORD -d master -i create-database.sql

#run the setup script to create the DB and the schema in the DB
#do this in a loop because the timing for when the SQL instance is ready is indeterminate

# Starting with SQL Server 2022 (16.x) CU 14 and SQL Server 2019 (15.x) CU 28,
# the container images include the new mssql-tools18 package.
# The previous directory /opt/mssql-tools/bin is being phased out.
# The new directory for Microsoft ODBC 18 tools is /opt/mssql-tools18/bin,
# aligning with the latest tools offering.
SQLCMDAPPPATH="/opt/mssql-tools18/bin/sqlcmd"

if [ -d /opt/mssql-tools/bin ]; then
    echo "/opt/mssql-tools/bin directory exists"
    SQLCMDAPPPATH="/opt/mssql-tools/bin/sqlcmd"
else
    echo "/opt/mssql-tools/bin directory DOESN'T exists"
    if [ -d /opt/mssql-tools18/bin ]; then
        echo "/opt/mssql-tools18/bin directory exists"
        SQLCMDAPPPATH="/opt/mssql-tools18/bin/sqlcmd"
    else
        echo "Not found path to sqlcmd util!!"
    fi
fi

for i in {1..50};
do
    $SQLCMDAPPPATH -S localhost -U sa -P $MSSQL_SA_PASSWORD -d master -C -i create-database.sql
    if [ $? -eq 0 ]
    then
        echo "setup.sql completed"
        break
    else
        echo "not ready yet..."
        sleep 1
    fi
done