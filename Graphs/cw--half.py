# -*- coding: utf-8 -*-
"""
Created on Sun Mar  7 11:24:10 2021

@author: dakol
"""

# -*- coding: utf-8 -*-
"""
Created on Sat Jun 27 10:38:25 2020

@author: dakol
"""

# -*- coding: utf-8 -*-
"""
Created on Mon Jun 15 22:13:30 2020

@author: dakol
"""

import numpy as np
import itertools
import matplotlib.pyplot as plt
import seaborn as sns
import matplotlib.patches as mpatches


# Libraries

from matplotlib import rc
import pandas as pd


#Benefit tye 1 , hobby courses
def drawPieMarker(xs, ys, ratios, sizes, colors):
    assert sum(ratios) <= 1.1, 'sum of ratios needs to be < 1'


    markers = []
    previous = 0
    
    # calculate the points of the pie pieces
    for color, ratio in zip(colors, ratios):
        this = 2 * np.pi * ratio + previous
        x  = [0] + np.cos(np.linspace(previous, this, 10)).tolist() + [0]
        y  = [0] + np.sin(np.linspace(previous, this, 10)).tolist() + [0]
        xy = np.column_stack([x, y])
        previous = this
        markers.append({'marker':xy, 's':np.abs(xy).max()**2*np.array(sizes), 'facecolor':color, "edgecolor":"k", "linewidth": 1 , 'linestyle': 'solid', 'antialiased': True})
    
    # scatter each of the pie pieces to create pies
    for marker in markers:
        ax.scatter(xs, ys, **marker)
        
n_groups=35
index = np.arange(n_groups, step=2)
fig , ax =plt.subplots(figsize=(15,5))


#fig, ax = plt.subplots()
drawPieMarker(xs=[1],
              ys=[16506],
              ratios=[0.261298922,
0.357021689,
0.381679389



],
              sizes=[500],
              colors=['white', 'grey', 'black'])

drawPieMarker(xs=[3],
              ys=[16095],
              ratios=[
0.26797142,
0.366138552,
0.365890028

],
              sizes=[500],
             colors=['white', 'grey', 'black'])

drawPieMarker(xs=[5],
              ys=[15801],
              ratios=[0.272957408,
0.372951079,
0.354091513

],
              sizes=[500],
              colors=['white', 'grey', 'black'])

drawPieMarker(xs=[7],
              ys=[15438],
              ratios=[0.279375567,
0.38172043,
0.338904003


],
              sizes=[500],
             colors=['white', 'grey', 'black'])

drawPieMarker(xs=[9],
              ys=[15083],
              ratios=[0.285951071,
0.390704767,
0.323344162





],
              sizes=[500],
             colors=['white', 'grey', 'black'])

drawPieMarker(xs=[11],
              ys=[14370],
              ratios=[0.300139179,
0.373556019,
0.326304802


],
              sizes=[500],
             colors=['white', 'grey', 'black'])

drawPieMarker(xs=[13],
              ys=[13319],
              ratios=[0.32382311,
0.269239432,
0.406937458

],
              sizes=[500],
              colors=['white', 'grey', 'black'])



drawPieMarker(xs=[15],
              ys=[12941],
              ratios=[0.333281817,
0.277103779,
0.389614404

],
              sizes=[500],
              colors=['white', 'grey', 'black'])

drawPieMarker(xs=[17],
              ys=[12019],
              ratios=[0.35884849,
0.298360929,
0.342790582


],
              sizes=[500],
              colors=['white', 'grey', 'black'])


drawPieMarker(xs=[19],
              ys=[11200],
              ratios=[0.385089286,
0.273303571,
0.341607143

],
              sizes=[500],
              colors=['white', 'grey', 'black'])

drawPieMarker(xs=[21],
              ys=[9710],
              ratios=[0.343872297,
0.36930999,
0.286817714


],
              sizes=[500],
              colors=['white', 'grey', 'black'])

drawPieMarker(xs=[23],
              ys=[9244],
              ratios=[0.36120727,
0.331133708,
0.307659022

],
              sizes=[500],
              colors=['white', 'grey', 'black'])

drawPieMarker(xs=[25],
              ys=[8729],
              ratios=[0.382518043,
0.35067018,
0.266811777


],
              sizes=[500],
              colors=['white', 'grey', 'black'])

drawPieMarker(xs=[27],
              ys=[7209],
              ratios=[0.463171036,
0.424608129,
0.112220835


],
              sizes=[500],
              colors=['white', 'grey', 'black'])

drawPieMarker(xs=[29],
              ys=[5097],
              ratios=[0.65509123,
0.103001766,
0.241907004

],
              sizes=[500],
              colors=['white', 'grey', 'black'])

drawPieMarker(xs=[31],
              ys=[2560],
              ratios=[0,
0.205078125,
0.794921875

],
              sizes=[500],
              colors=['white', 'grey', 'black'])

drawPieMarker(xs=[33],
              ys=[190],
              ratios=[0,
0,
1


],
              sizes=[500],
              colors=['white', 'grey', 'black'])
drawPieMarker(xs=[35],
              ys=[0],
              ratios=[0,
0,
0


],
              sizes=[500],
              colors=['white', 'grey', 'black'])

plt.ylabel("Total distributed benefit in H courses", fontsize=15)
plt.xlabel("Solution Number", fontsize=15)
red_patch = mpatches.Patch(color='grey', label='G1')
red_patch2 = mpatches.Patch(color='white', label='G2')
red_patch3 = mpatches.Patch(color='black', label='G3')
plt.legend(handles=[red_patch, red_patch2, red_patch3])
#plt.legend( labels=['G1', 'G2', 'G3'])
plt.xticks(index + 1, ('1', '3','5','7','9','11','13','15','17','19','21','23','25','27','29','31','33','35'))
plt.savefig('CW-piechartvoc.png')
plt.show()








#Benefit tye 1 , hobby courses
def drawPieMarker(xs, ys, ratios, sizes, colors):
    assert sum(ratios) <= 1.05, 'sum of ratios needs to be < 1'

    markers = []
    previous = 0
   
    # calculate the points of the pie pieces
    for color, ratio in zip(colors, ratios):
        this = 2 * np.pi * ratio + previous
        x  = [0] + np.cos(np.linspace(previous, this, 10)).tolist() + [0]
        y  = [0] + np.sin(np.linspace(previous, this, 10)).tolist() + [0]
        xy = np.column_stack([x, y])
        previous = this
        markers.append({'marker':xy, 's':np.abs(xy).max()**2*np.array(sizes), 'facecolor':color, "edgecolor":"k", "linewidth": 1 , 'linestyle': 'solid', 'antialiased': True})

    # scatter each of the pie pieces to create pies
    
    for marker in markers:
        ax.scatter(xs, ys, **marker)
      
n_groups=35
index = np.arange(n_groups, step=2)

fig , ax =plt.subplots(figsize=(15,5))

drawPieMarker(xs=[1],
              ys=[0],
              ratios=[0,
0,
0



],
              sizes=[500],
              colors=['white', 'grey', 'black'])
drawPieMarker(xs=[3],
              ys=[310],
              ratios=[0,
0,
1


],
              sizes=[500],
             colors=['white', 'grey', 'black'])
drawPieMarker(xs=[5],
              ys=[577],
              ratios=[0,
0,
1

],
              sizes=[500],
              colors=['white', 'grey', 'black'])
drawPieMarker(xs=[7],
              ys=[860],
              ratios=[0,
0,
1


],
              sizes=[500],
             colors=['white', 'grey', 'black'])
drawPieMarker(xs=[9],
              ys=[1096],
              ratios=[0,
0.333029197,
0.666970803


],
              sizes=[500],
             colors=['white', 'grey', 'black'])
drawPieMarker(xs=[11],
              ys=[1670],
              ratios=[0,
0.218562874,
0.781437126


],
              sizes=[500],
             colors=['white', 'grey', 'black'])
drawPieMarker(xs=[13],
              ys=[2572],
              ratios=[0,
0.826982893,
0.173017107

],
              sizes=[500],
              colors=['white', 'grey', 'black'])

drawPieMarker(xs=[15],
              ys=[2882],
              ratios=[0,
0.738029146,
0.261970854

],
              sizes=[500],
              colors=['white', 'grey', 'black'])
drawPieMarker(xs=[17],
              ys=[3539],
              ratios=[0,
0.601017237,
0.398982763



],
              sizes=[500],
              colors=['white', 'grey', 'black'])
drawPieMarker(xs=[19],
              ys=[3440],
              ratios=[0.580813953,
0.106104651,
0.313081395


],
              sizes=[500],
              colors=['white', 'grey', 'black'])
drawPieMarker(xs=[21],
              ys=[4449],
              ratios=[0.449089683,
0.082040908,
0.468869409



],
              sizes=[500],
              colors=['white', 'grey', 'black'])
drawPieMarker(xs=[23],
              ys=[4787],
              ratios=[0.417380405,
0.444328389,
0.138291205

],
              sizes=[500],
              colors=['white', 'grey', 'black'])

drawPieMarker(xs=[25],
              ys=[5226],
              ratios=[0.382319173,
0.407003444,
0.210677382

],
              sizes=[500],
              colors=['white', 'grey', 'black'])

drawPieMarker(xs=[27],
              ys=[6452],
              ratios=[0.30967142,
0.32966522,
0.36066336


],
              sizes=[500],
              colors=['white', 'grey', 'black'])

drawPieMarker(xs=[29],
              ys=[7980],
              ratios=[0.25037594,
0.312280702,
0.437343358

],
              sizes=[500],
              colors=['white', 'grey', 'black'])
drawPieMarker(xs=[31],
              ys=[9922],
              ratios=[0.201370691,
0.375932272,
0.422697037


],
              sizes=[500],
              colors=['white', 'grey', 'black'])
drawPieMarker(xs=[33],
              ys=[11798],
              ratios=[0.169350737,
0.347092728,
0.483556535


],
              sizes=[500],
              colors=['white', 'grey', 'black'])
drawPieMarker(xs=[35],
              ys=[11952],
              ratios=[0.167168675,
0.342620482,
0.490210843


],
              sizes=[500],
              colors=['white', 'grey', 'black'])
plt.xticks(index + 1, ('1', '3','5','7','9','11','13','15','17','19','21','23','25','27','29','31','33','35'))
plt.ylabel("Total distributed benefit in VA courses", fontsize=15)
plt.xlabel("Solution Number", fontsize=15)
red_patch = mpatches.Patch(color='grey', label='G1')
red_patch2 = mpatches.Patch(color='white', label='G2')
red_patch3 = mpatches.Patch(color='black', label='G3')
plt.legend(handles=[red_patch, red_patch2, red_patch3])
#plt.legend(labels=['G1','G2','G3'])
plt.savefig('CW-piecharthobby.png')
plt.show()