Conditions: For each of the six knapPI files:   
1. We have tested three specific cases    
2. For each specific case, we tested our algorithm (Best Evolved)    
with configurations as followes:
 - Best of 10 runs of Best Evolved
 - 1000 mutations or generations (whatever you want to call it)
 - one mutation rate of 1/10th the size of the total knapsack size
 - one mutation rate of 1/5th the size of the toatl knapsack size <br />  
3. For each specific case, we tested the random-search with 1000
mutations or generations (Whatever you want to call it)

Results

*knapPI_1000_1000.clf*   
knapPI_1000_1000_1:  <br />
 -Best Evolved (100 Mutations): -158834N   
 -Best Evolved (200 Mutations): -168302N    
 -Random: -196346N   
knapPI_1000_1000_15:   
 -Best Evolved (100 Mutations): -183759N    
 -Best Evolved (200 Mutations): -233914N     
 -Random: -288574N     
knapPI_1000_1000_30    
 -Best Evolved (100 Mutations): -8011N   
 -Best Evolved (200 Mutations): -21678N   
 -Random: -41386N    

*knapPI_200_1000.clf*   
knapPI_200_1000_1   
 -Best Evolved (20 Mutations): -17529N  
 -Best Evolved (40 Mutations): -22303N  
 -Random: -33122N  
knapPI_200_1000_15  
 -Best Evolved (20 Mutations): -10397N  
 -Best Evolved (40 Mutations): -22968N  
 -Random: -41980N  
knapPI_200_1000_30   
 -Best Evolved (20 Mutations): 51678N   
 -Best Evolved (40 Mutations): 45180N   
 -Random: -4018N  

*knapPI_20_1000.clf*
knapPI_20_1000_1   
 -Best Evolved (2 Mutations): 1428N   
 -Best Evolved (4 Mutations): 1428N    
 -Random: 624N   
knapPI_20_1000_15    
 -Best Evolved (2 Mutations): 2786N  
 -Best Evolved (4 Mutations): 2786N  
 -Random: -435N  
knapPI_20_1000_30  
 -Best Evolved (2 Mutations): 5635N  
 -Best Evolved (4 Mutations): 5635N  
 -Random: 4913N  

*Best Evolved*   
knapPI_16_20_1000_1   
2176 		-with 1/10 randomly flipped mutations   
2239 		-with 2/5 randomly flipped mutations   
knapPI_16_200_1000_13   <br />
24172 		-with 1/10 randomly flipped mutations  
-11694 	-with 2/5 randomly flipped mutations  
knapPI_16_1000_1000_5     
-152982 	-with 1/10 randomly flipped mutations  
-173151 	-with 2/5 randomly flipped mutations  
knapPI_16_20_1000_2  
1971 		-with 1/10 randomly flipped mutations  
2260 		-with 2/5 randomly flipped mutations  
knapPI_16__200_14  
-3173 		-with 1/10 randomly flipped mutations  
-9665 		-with 2/5 randomly flipped mutations  
knapPI_16_20_1000_6  
-14625 	-with 1/10 randomly flipped mutations  
-167244  	-with 2/5 randomly flipped mutations  
knapPI_13_20_1000_1  
1677		-with 1/10 randomly flipped mutations  
1677		-with 2/5 randomly flipped mutations  
knapPI_13_200_1000_13  
-9279		-with 1/10 randomly flipped mutations  
-21843		-with 2/5 randomly flipped mutations  
knapPI_13_1000_1000_5  
-95548		-with 1/10 randomly flipped mutations  
-111487	-with 2/5 randomly flipped mutations  
knapPI_13_20_1000_2  
2372		-with 1/10 randomly flipped mutations  
2501		-with 2/5 randomly flipped mutations  
knapPI_13_200_1000_14  
-7044		-with 1/10 randomly flipped mutations  
-2027		-with 2/5 randomly flipped mutations  
knapPI_13_1000_1000_6  
-184244	-with 1/10 randomly flipped mutations  
-210510	-with 2/5 randomly flipped mutations  

*Random*  
knapPI_16_20_1000_1  
-1024  
knapPI_16_200_1000_13  
-2206  
knapPI_16_1000_1000_5  
-32626  
knapPI_16_20_1000_2  
1565  
knapPI_16_200_1000_14  
-2403  
knapPI_16_1000_1000_6  
-30338  
knapPI_13_20_1000_1  
1248  
knapPI_13_200_1000_13  
-39089  
knapPI_13_1000_1000_5  
-19336  
knapPI_13_20_1000_2  
2318  
knapPI_13_200_1000_14  
-38432  
knapPI_13_1000_1000_6  
-36587  
