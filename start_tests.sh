#!/bin/bash

mvn clean package install
firefox -new-tab "./target/site/allure-maven-plugin/index.html"

