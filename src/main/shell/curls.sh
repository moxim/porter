#!/bin/bash
#
# Here is a set of curl scripts to run against a server as specified in the docker-compose.yaml.
#

SERVER=78.46.36.172

doTheFunkyStuff() {
  local port=$1
  curl -s http://$SERVER:$port/kuckuck >/dev/null
  if [ $? -ne 0 ]; then
      echo NOK.
      return 1
  else
      echo OK.
      return 0
  fi
  return 0
}

checkPorts() {
  local result=0

  allPorts=(80 9 443 3000 3443 4000 4001 4200 4201 5080 5601 6080 8080 9200 9990)

  for port in ${allPorts[@]} ; do
    echo -n "Checking port $port: "
    doTheFunkyStuff $port
    result=$(($result + $?))
  done

  return $result
}

checkPorts
result=$?
echo $result

if [ "X$result" != "X0" ]; then
  echo Not all ports are reachable.
  exit 1
else
  echo "All ports are reachable."
  exit 0
fi
