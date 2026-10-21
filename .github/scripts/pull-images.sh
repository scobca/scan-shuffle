#!/usr/bin/env bash
set -euo pipefail

ssh -o BatchMode=yes "$SERVER_USER@$SERVER_HOST" <<'EOF'
  set -Eeuo pipefail
  cd "$HOME/scan-shuffle"
  docker compose --env-file .env -p scan-shuffle -f docker-compose.yml pull
  docker compose --env-file .env -p scan-shuffle -f docker-compose.yml up -d --remove-orphans
  docker compose --env-file .env -p scan-shuffle -f docker-compose.yml ps
EOF
