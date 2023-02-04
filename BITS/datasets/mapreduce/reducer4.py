#!/usr/bin/env python3
import sys
from datetime import datetime

delimiter = ","
chemicals_removed = {}


def reduce():
    my_iterator = iter(sys.stdin.readline, "")
    header = next(my_iterator)
    chemical_name_header, chemical_date_removed_header = header.strip().split(delimiter)
    print(f"{chemical_name_header}{delimiter}{chemical_date_removed_header}")

    for line in sys.stdin:
        line = line.strip()

        chemical_name, chemical_date_removed = line.split(delimiter)

        if chemical_date_removed is not None and "/" in chemical_date_removed:
            chemical_date_removed = datetime.strptime(chemical_date_removed, "%m/%d/%Y").strftime("%Y/%m/%d")

            if chemical_name in chemicals_removed.keys():
                if chemical_date_removed > chemicals_removed[chemical_name]:
                    chemicals_removed[chemical_name] = chemical_date_removed
            else:
                chemicals_removed[chemical_name] = chemical_date_removed

    sorted_chemicals_removed = sorted(chemicals_removed.items(), key=lambda kv: (kv[1], kv[0]), reverse=True)[0:5]

    for chemicals in sorted_chemicals_removed:
        print(f"{chemicals[0]}")


if __name__ == "__main__":
    reduce()
