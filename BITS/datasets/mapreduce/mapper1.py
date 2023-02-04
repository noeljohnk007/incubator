#!/usr/bin/env python3
import sys

delimiter = ","


def map():
    for line in sys.stdin:
        rows = line.strip()
        columns = rows.split(delimiter)

        if len(columns) == 23:
            product_name = columns[2]
            company_name = columns[6]

            print(f"{company_name}{delimiter}{product_name}")


if __name__ == "__main__":
    map()
