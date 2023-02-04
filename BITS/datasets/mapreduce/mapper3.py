#!/usr/bin/env python3
import sys

delimiter = ","


def map():
    for line in sys.stdin:
        rows = line.strip()
        columns = rows.split(delimiter)

        if len(columns) == 23:
            primary_category = columns[9]
            discontinued_date = columns[18]

            print(f"{primary_category}{delimiter}{discontinued_date}")


if __name__ == "__main__":
    map()
