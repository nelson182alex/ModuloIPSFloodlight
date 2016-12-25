#!/bin/sh

curl http://localhost:8080/wm/firewall/module/enable/json -X PUT -d
curl -X POST -d '{"src-ip": "10.0.0.1/32", "dst-ip": "10.0.0.3/32", "nw-proto":"UDP" }' http://localhost:8080/wm/firewall/rules/json
curl -X POST -d '{"src-ip": "10.0.0.2/32", "dst-ip": "10.0.0.3/32", "nw-proto":"UDP" }' http://localhost:8080/wm/firewall/rules/json
curl -X POST -d '{"src-ip": "10.0.0.3/32", "dst-ip": "10.0.0.3/32", "nw-proto":"UDP" }' http://localhost:8080/wm/firewall/rules/json
curl -X POST -d '{"src-ip": "10.0.0.4/32", "dst-ip": "10.0.0.3/32", "nw-proto":"UDP" }' http://localhost:8080/wm/firewall/rules/json
curl -X POST -d '{"src-ip": "10.0.0.5/32", "dst-ip": "10.0.0.3/32", "nw-proto":"UDP" }' http://localhost:8080/wm/firewall/rules/json
curl -X POST -d '{"src-ip": "10.0.0.6/32", "dst-ip": "10.0.0.3/32", "nw-proto":"UDP" }' http://localhost:8080/wm/firewall/rules/json
curl -X POST -d '{"src-ip": "10.0.0.1/32", "dst-ip": "10.0.0.3/32", "nw-proto":"TCP" }' http://localhost:8080/wm/firewall/rules/json
curl -X POST -d '{"src-ip": "10.0.0.2/32", "dst-ip": "10.0.0.3/32", "nw-proto":"TCP" }' http://localhost:8080/wm/firewall/rules/json
curl -X POST -d '{"src-ip": "10.0.0.3/32", "dst-ip": "10.0.0.3/32", "nw-proto":"TCP" }' http://localhost:8080/wm/firewall/rules/json
curl -X POST -d '{"src-ip": "10.0.0.4/32", "dst-ip": "10.0.0.3/32", "nw-proto":"TCP" }' http://localhost:8080/wm/firewall/rules/json
curl -X POST -d '{"src-ip": "10.0.0.5/32", "dst-ip": "10.0.0.3/32", "nw-proto":"TCP" }' http://localhost:8080/wm/firewall/rules/json
curl -X POST -d '{"src-ip": "10.0.0.6/32", "dst-ip": "10.0.0.3/32", "nw-proto":"TCP" }' http://localhost:8080/wm/firewall/rules/json
