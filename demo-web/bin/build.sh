#!/usr/bin/env bash

CURRENT_DIR=$(dirname $0)

[ -d ${CURRENT_DIR}/demo-web.tar.gz ] && rm ${CURRENT_DIR}/demo-web.tar.gz
cp ${CURRENT_DIR}/../demo-web.jar demo-web.jar
tar -zcvf ${CURRENT_DIR}/demo-web.tar.gz ${CURRENT_DIR}/run.conf ${CURRENT_DIR}/run.sh ${CURRENT_DIR}/static.conf ${CURRENT_DIR}/demo-web.jar
rm -rf ${CURRENT_DIR}/demo-web.jar
