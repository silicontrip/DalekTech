#!/bin/sh


for n in $@

do

cat $n | sed 's/package org.silicontrip.dalektech.ui;/package org.silicontrip.dalektech.ui.gui;/' > ${n}~

done
