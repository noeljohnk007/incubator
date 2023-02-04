#!/usr/bin/env python3
import sys
import collections

delimiter = ","
primary_category_with_discontinued_chemicals = collections.Counter()


def reduce():
    my_iterator = iter(sys.stdin.readline, "")
    header = next(my_iterator)
    primary_category_header, discontinued_date_header = header.strip().split(delimiter)
    print(f"{primary_category_header}")

    for line in sys.stdin:
        line = line.strip()

        primary_category, discontinued_date = line.split(delimiter)

        if discontinued_date is not None and discontinued_date!= "":
            if primary_category in primary_category_with_discontinued_chemicals.keys():
                count = primary_category_with_discontinued_chemicals[primary_category]
                primary_category_with_discontinued_chemicals[primary_category] = count + 1
            else:
                primary_category_with_discontinued_chemicals[primary_category] = 1

    print(primary_category_with_discontinued_chemicals.most_common(1)[0][0])


if __name__ == "__main__":
    reduce()
