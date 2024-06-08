#!/bin/bash
# Run Microsoft SQl Server and initialization script (at the same time) 
#/opt/mssql/bin/sqlservr
/usr/src/app/run-initialization.sh & /opt/mssql/bin/sqlservr