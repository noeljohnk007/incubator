#!/usr/bin/env python3
import sys

delimiter = ","
content_separator = "|"
unique_products = {}


def reduce():
    my_iterator = iter(sys.stdin.readline, "")
    header = next(my_iterator)
    company_name_header, brand_name_header, product_name_header, chemical_name_header = header.strip().split(delimiter)
    print(f"{company_name_header}{delimiter}{brand_name_header}{delimiter}{product_name_header}{delimiter}ChemicalCount{delimiter}{chemical_name_header}")

    for line in sys.stdin:
        line = line.strip()

        company_name, brand_name, product_name, chemical_name = line.split(delimiter)
        key = f"{company_name}{delimiter}{brand_name}{delimiter}{product_name}"

        if key in unique_products.keys():
            unique_chemicals = unique_products[key]
            unique_chemicals.add(chemical_name)
        else:
            unique_chemicals = set()
            unique_chemicals.add(chemical_name)
            unique_products[key] = unique_chemicals

    for key in unique_products.keys():
        print(f"{key}{delimiter}{len(unique_products[key])}{delimiter}{content_separator.join(unique_products[key])}")


if __name__ == "__main__":
    reduce()
