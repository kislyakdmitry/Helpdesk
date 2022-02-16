## Command for deploying database in Docker: 
```sh
sudo docker run --name helpdesk-postgres2 -p 5433:5432 -e POSTGRES_USER=helpdesk-user -e POSTGRES_PASSWORD=1234qwerty -e POSTGRES_DB=helpdeskdb -d postgres:14.1
```
