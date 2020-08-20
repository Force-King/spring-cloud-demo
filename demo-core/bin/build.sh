#!/usr/bin/env bash

CURRENT_DIR=$(dirname $0)

[ -d ${CURRENT_DIR}/core.tar.gz ] && rm ${CURRENT_DIR}/demo-core.tar.gz
cp ${CURRENT_DIR}/../demo-core.jar demo-core.jar
tar -zcvf ${CURRENT_DIR}/demo-core.tar.gz ${CURRENT_DIR}/run.conf ${CURRENT_DIR}/run.sh ${CURRENT_DIR}/static.conf ${CURRENT_DIR}/demo-core.jar
rm -rf ${CURRENT_DIR}/demo-core.jar
