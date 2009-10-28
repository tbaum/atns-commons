#!/bin/bash
set -e
cd target
rm -rf sync
mkdir sync
cd sync
unzip ../trayapp-1.3-SNAPSHOT.zip
cp -R ../../src/main/jnlp/resources .
rsync -vacr * ssh-229593@www.atns.de:www/atns/shop
cd ../..
