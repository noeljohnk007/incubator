#!/usr/bin/env python3
import sys

delimiter = ","


def map():
    for line in sys.stdin:
        rows = line.strip()
        columns = rows.split(delimiter)

        if len(columns) == 23:
            chemical_name = columns[15]
            chemical_date_removed = columns[21]

            print(f"{chemical_name}{delimiter}{chemical_date_removed}")


if __name__ == "__main__":
    map()
