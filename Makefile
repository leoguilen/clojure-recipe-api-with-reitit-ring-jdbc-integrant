docker-up:
	docker compose -f .docker/compose.yaml -p cheffy up -d

docker-down:
	docker compose -p cheffy down

pg-select:
	docker exec -it postgres psql -d cheffy -U postgres -w postgres -c "$(COMMAND)"