#!/usr/bin/env python3
import sys

delimiter = ","
total_products = set()
unique_products = {}


def reduce():
    my_iterator = iter(sys.stdin.readline, "")
    header = next(my_iterator)
    company_name_header, product_name_header = header.strip().split(delimiter)
    print(f"{company_name_header}{delimiter}ProductsCount")

    for line in sys.stdin:
        line = line.strip()

        company_name, product_name = line.split(delimiter)
        key = f"{company_name}{delimiter}{product_name}"
        total_products.add(key)

    for key in total_products:
        company_name, product_name = key.split(delimiter)

        if company_name in unique_products.keys():
            count = unique_products[company_name]
            unique_products[company_name] = count + 1
        else:
            unique_products[company_name] = 1

    for company_name in unique_products.keys():
        print(f"{company_name}{delimiter}{unique_products[company_name]}")


if __name__ == "__main__":
    reduce()
