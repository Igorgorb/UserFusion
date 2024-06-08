#!/bin/bash
# Wait to be sure that SQL Server came up
#sleep 120s
#
## Run the setup script to create the DB and the schema in the DB
## Note: make sure that your password matches what is in the Dockerfile
#/opt/mssql-tools/bin/sqlcmd -S localhost -U sa -P $MSSQL_SA_PASSWORD -d master -i create-database.sql

#run the setup script to create the DB and the schema in the DB
#do this in a loop because the timing for when the SQL instance is ready is indeterminate
for i in {1..50};
do
    /opt/mssql-tools/bin/sqlcmd -S localhost -U sa -P $MSSQL_SA_PASSWORD -d master -i create-database.sql
    if [ $? -eq 0 ]
    then
        echo "setup.sql completed"
        break
    else
        echo "not ready yet..."
        sleep 1
    fi
done
#!/bin/bash

# Wait 60 seconds for SQL Server to start up by ensuring that
# calling SQLCMD does not return an error code, which will ensure that sqlcmd is accessible
# and that system and user databases return "0" which means all databases are in an "online" state
# https://docs.microsoft.com/en-us/sql/relational-databases/system-catalog-views/sys-databases-transact-sql?view=sql-server-2017
#
#DBSTATUS=1
#ERRCODE=1
#i=0
#
#while [[ $DBSTATUS -ne 0 ]] && [[ $i -lt 60 ]] && [[ $ERRCODE -ne 0 ]]; do
#	i=$i+1
#	DBSTATUS=$(/opt/mssql-tools/bin/sqlcmd -h -1 -t 1 -U sa -P $MSSQL_SA_PASSWORD -Q "SET NOCOUNT ON; Select SUM(state) from sys.databases")
#	ERRCODE=$?
#	sleep 1
#done
#
#if [ $DBSTATUS -ne 0 ] OR [ $ERRCODE -ne 0 ]; then
#	echo "SQL Server took more than 60 seconds to start up or one or more databases are not in an ONLINE state"
#	exit 1
#fi
#
## Run the setup script to create the DB and the schema in the DB
#/opt/mssql-tools/bin/sqlcmd -S localhost -U sa -P $MSSQL_SA_PASSWORD -d master -i create-database.sql