docker-up:
	docker compose -f .docker/compose.yaml -p cheffy up --build -d

docker-down:
	docker compose -p cheffy down -v

pg-select:
	docker exec -it postgres psql -d cheffy -U postgres -w postgres -c "$(COMMAND)"