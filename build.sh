#!/bin/bash

SRCS="ir.cpp ir_printer.cpp main.cpp var.cpp"
OUT="mote"

g++ -std=c++11 $SRCS -o $OUT
