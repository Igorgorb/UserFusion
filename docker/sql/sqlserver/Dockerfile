# Choose ubuntu version
FROM mcr.microsoft.com/mssql/server:2022-latest

USER root
# Create app directory
RUN mkdir -p /usr/src/app
WORKDIR /usr/src/app

# Copy initialization scripts
COPY ./sql/sqlserver /usr/src/app

# Grant permissions for the run-initialization script to be executable
RUN chmod +x /usr/src/app/entrypoint.sh
RUN chmod +x /usr/src/app/run-initialization.sh

# Set environment variables, not have to write them with the docker run command
# Note: make sure that your password matches what is in the run-initialization script 
ENV MSSQL_SA_PASSWORD verYs3cret
ENV SA_PASSWORD verYs3cret
ENV ACCEPT_EULA Y
ENV MSSQL_PID Express

# Expose port 1433 in case accessing from other container
# Expose port externally from docker-compose.yml
EXPOSE 1433

# Switch back to mssql user and run the entrypoint script
USER mssql
# Run Microsoft SQL Server and initialization script (at the same time)
CMD /bin/bash ./entrypoint.sh
# ENTRYPOINT ["./entrypoint.sh"]