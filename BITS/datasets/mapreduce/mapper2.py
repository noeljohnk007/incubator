#!/usr/bin/env python3
import sys

delimiter = ","


def map():
    for line in sys.stdin:
        rows = line.strip()
        columns = rows.split(delimiter)

        if len(columns) == 23:
            company_name = columns[6]
            brand_name = columns[7]
            product_name = columns[2]
            chemical_name = columns[15]

            print(f"{company_name}{delimiter}{brand_name}{delimiter}{product_name}{delimiter}{chemical_name}")


if __name__ == "__main__":
    map()
