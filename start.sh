#!/bin/bash

git config http.proxy http://127.0.0.1:7890
git config https.proxy https://127.0.0.1:7890
~/.tmp/.clash/clash -d ~/.tmp/.clash
