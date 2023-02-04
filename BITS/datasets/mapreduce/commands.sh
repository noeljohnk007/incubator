1. Total products launched by a Company - company name, productname - company name and count of product name
2. Which product has the higher chemical count - company name, brand name, product name, chemical name - company name, brand name, product name, chemical names
2. Find all the chemicals associated with a products
3. Which primary category has highest discontinued chemicals - pri. cat., dis. date, - pri. cat.
4. 5 latest removed chemicals - chem. name, chem. date rem. - chem. name, chem. date rem.

hadoop jar libs/hadoop-streaming-3.3.4.jar -files mapper.py,reducer.py,input.csv -mapper mapper.py -reducer reducer.py -input input.csv -output output

cat .\chemicals-in-cosmetics-3.csv | python .\mapper.py | python .\reducer.py
cat .\input.csv | python .\mapper1.py | python .\mapper2.py | python .\reducer1.py | python .\reducer2.py

hadoop jar libs/hadoop-streaming-3.3.4.jar -files mapper.py,reducer.py,chemicals-in-cosmetics-3.csv -mapper mapper.py -reducer reducer.py -input chemicals-in-cosmetics-3.csv -output output
hadoop jar libs/hadoop-streaming-3.3.4.jar -files mapper.py,combiner.py,reducer.py,chemicals-in-cosmetics-3.csv mapper.py -mapper mapper.py -combiner combiner.py -reducer reducer.py -input chemicals-in-cosmetics-3.csv -output output

assumptions
- values containing commas are neglected since the comma is considered as a separtor
- removal of header is not done
- instead of ids we are taking name as the primary key
- 2nd part is not done