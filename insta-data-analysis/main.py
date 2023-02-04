import json 
 
with open('following.json', 'r') as following: 
    parsed_following = json.load(following) 
 
with open('followers.json', 'r') as followers: 
    parsed_followers = json.load(followers) 
 
following_list, followers_list = [], []
 
 
for var_following in parsed_following["relationships_following"]: 
    following_list.append(((var_following['string_list_data'])[0])['value']) 

print("helo")
 
for var_followers in parsed_followers["relationships_followers"]: 
    followers_list.append(((var_followers['string_list_data'])[0])['value']) 
 
for investment in following_list: 
    if investment not in followers_list: 
        print(investment) 