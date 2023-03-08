# -*- coding: utf-8 -*-
"""
Created on Mon Jun 15 23:43:04 2020

@author: dakol
"""

import numpy as np
import itertools
import matplotlib.pyplot as plt
import seaborn as sns

# Libraries

from matplotlib import rc
import pandas as pd

# data to plot
n_groups = 29
means_frank = (0,	0	,0	,0,	0,	0	,0	,0	,0	,0,	0.517270329,	0.519889103,	0.492239215	,0.492239215,	0.496260499 ,0.387130435,	0.395850622,	0.38450023,	0.368461708	,0.335240964,	0.570200952,	0.556372549,	0.539057618,	0.436014625,	0.448127768	,0.433636364,	0.416801897	,0.256040368,	0.261298922)
means_guido = (0,	0	,0	,0	,0	,0	,0	,0	,0	,0	,0,	0,	0,	0	,0, 0,	0,	0	,0	,0,	0.316941624	,0.324878049,	0.336024218	,0.324878049,	0.316941624	,0.327540984,	0.34507772	,0	,0)

# create plot
fig, ax = plt.subplots()
index = np.arange(n_groups)
plt.figure(figsize=(15, 5))
bar_width = 0.35
opacity = 0.8

rects1 = plt.bar(index, means_frank, bar_width,
alpha=opacity,
color='white',
edgecolor='black',
label='H')

rects2 = plt.bar(index + bar_width, means_guido, bar_width,
alpha=opacity,
color='white',
edgecolor='black',
hatch="//",
label='VA')


plt.xticks(index + bar_width/2, ('1', '2', '3', '4','5','6','7','8','9','10','11','12','13','14','15','16','17','18','19','20','21','22','23','24','25','26','27','28','29'))


plt.ylim(0,1)
plt.ylabel("Satisfied demand", fontsize=18)
plt.xlabel("Solution number", fontsize=18)
#plt.title('Her bir solution G1 icin hobby and vocatonal what persentage satisfied')
plt.legend(labels=['H','VA'])
plt.tight_layout()
plt.savefig('G1.png')
plt.show()

# data to plot 2
n_groups = 29
means_frank = (1,	0.971306753,	0.977955679,	0.979221347,	0.922815853	,0.930764134,	0.919995634,	0.419317119,	0.42069451,	0.398178992	,0.430079156,	0.368972999,	0.409267291,	0.409267291,0.352203429 ,0.354898551,	0.362892709,	0.352487333	,0.337784154,	0.307329317	,0.404680063,	0.394865841,	0.382577178	,0.468268477,	0.41081734	,0.397532468,	0.382099613,	0.500385871,	0.357021689)
means_guido = (0.307859314	,0.317225951,	0.371385991	,0.394615994	,0.33649739,	0.39231738,	0.397765363,	0.317225951,	0.372719115	,0.400449944	,0.317225951,	0.317225951,	0.33649739,	0.394615994,	0.39380531, 0.335224586	,0.385221827,	0.397765363,	0.418401612,	0.477303199	,0.337404822,0.345853659,	0.357719475	,0.345853659	,0.337404822,	0.348688525	,0.367357513	,0,	0)

fig, ax = plt.subplots()
index = np.arange(n_groups)
plt.figure(figsize=(15, 5))
bar_width = 0.35
opacity = 0.8

rects1 = plt.bar(index, means_frank, bar_width,
alpha=opacity,
color='white',
edgecolor='black',
label='H')

rects2 = plt.bar(index + bar_width, means_guido, bar_width,
alpha=opacity,
color='white',
edgecolor='black',
hatch="//",
label='VA')

plt.xticks(index + bar_width/2, ('1', '2', '3', '4','5','6','7','8','9','10','11','12','13','14','15','16','17','18','19','20','21','22','23','24','25','26','27','28','29'))
#plt.xticks(index + bar_width/2, ('1', '2', '3', '4','5','6','7','8','9','10','11','12','13','14','15','16','17','18','19','20','21','22'))


plt.ylim(0,1)
plt.ylabel("Satisfied demand", fontsize=18)
plt.xlabel("Solution number", fontsize=18)
#plt.title('Her bir solution G2 icin hobby and vocatonal what persentage satisfied')
plt.legend(labels=['H','VA'])
plt.tight_layout()
plt.savefig('G2.png')
plt.show()

# data to plot 3
n_groups = 29
means_frank = (0,	0.028693247	,0.022044321,	0.020778653	,0.077184147,	0.069235866	,0.080004366,	0.580682881,	0.57930549	,0.601821008,	0.052650516,	0.111137898,	0.098493495	,0.098493495,	0.151536072, 0.257971014	,0.241256669,	0.263012437	,0.293754138	,0.357429719	,0.025118985	,0.04876161	,0.078365204,	0.095716897	,0.141054892,	0.168831169	,0.20109849	,0.243573761	,0.381679389)
means_guido = (0.692140686,	0.682774049,	0.628614009	,0.605384006,	0.66350261	,0.60768262,	0.602234637	,0.682774049,	0.627280885,	0.599550056,	0.682774049,	0.682774049	,0.66350261,	0.605384006	,0.60619469 ,0.664775414	,0.614778173	,0.602234637	,0.581598388	,0.522696801	,0.345653553	,0.329268293,	0.306256307	,0.329268293	,0.345653553	,0.323770492	,0.287564767	,0	,0)

fig, ax = plt.subplots()
index = np.arange(n_groups)
plt.figure(figsize=(15, 5))
bar_width = 0.35
opacity = 0.8

rects1 = plt.bar(index, means_frank, bar_width,
alpha=opacity,
color='white',
edgecolor='black',
label='H')

rects2 = plt.bar(index + bar_width, means_guido, bar_width,
alpha=opacity,
color='white',
edgecolor='black',
hatch="//",
label='VA')

plt.xticks(index + bar_width/2, ('1', '2', '3', '4','5','6','7','8','9','10','11','12','13','14','15','16','17','18','19','20','21','22','23','24','25','26','27','28','29'))
#plt.xticks(index + bar_width/2, ('1', '2', '3', '4','5','6','7','8','9','10','11','12','13','14','15','16','17','18','19','20','21','22'))


plt.ylim(0,1)
plt.ylabel("Satisfied demand", fontsize=18)
plt.xlabel("Solution number", fontsize=18)
#plt.title('Her bir solution G3 icin hobby and vocatonal what persentage satisfied')
plt.legend(labels=['H','VA'])
plt.tight_layout()
plt.savefig('G3.png')
plt.show()