#!/bin/sh
# wait-for-it.sh: Wait for MySQL to be ready

host="$1"
shift
port="$1"
shift

echo "Waiting for $host:$port..."

while ! nc -z "$host" "$port"; do
  sleep 1
done

echo "$host:$port is up - executing command"
exec "$@"
