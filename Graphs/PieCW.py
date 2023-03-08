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
index = np.arange(n_groups)
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
drawPieMarker(xs=[2],
              ys=[16316],
              ratios=[0.26434175,
0.361179211,
0.374479039



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
drawPieMarker(xs=[4],
              ys=[15920],
              ratios=[
0.270917085,
0.33718593,
0.391896985



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
drawPieMarker(xs=[6],
              ys=[15626],
              ratios=[0.276014335,
0.377127864,
0.346857801




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
drawPieMarker(xs=[8],
              ys=[15189],
              ratios=[0.283955494,
0.387978142,
0.328066364





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
drawPieMarker(xs=[10],
              ys=[14913],
              ratios=[0.289210756,
0.359954402,
0.350834842


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
drawPieMarker(xs=[12],
              ys=[13756],
              ratios=[0.313535912,
0.390229718,
0.29623437



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


drawPieMarker(xs=[14],
              ys=[13131],
              ratios=[0.328459371,
0.273094205,
0.398446424

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
drawPieMarker(xs=[16],
              ys=[12692],
              ratios=[0.339820359,
0.282540183,
0.377639458


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

drawPieMarker(xs=[18],
              ys=[11561],
              ratios=[0.373064614,
0.264769484,
0.362165903



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
drawPieMarker(xs=[20],
              ys=[10485],
              ratios=[0.318454936,
0.342012399,
0.339532666


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
drawPieMarker(xs=[22],
              ys=[9486],
              ratios=[0.35199241,
0.322686064,
0.325321526



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
drawPieMarker(xs=[24],
              ys=[9108],
              ratios=[0.366600791,
0.336078173,
0.297321036

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
drawPieMarker(xs=[26],
              ys=[8011],
              ratios=[0.416801897,
0.382099613,
0.20109849


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
drawPieMarker(xs=[28],
              ys=[6089],
              ratios=[0.548365906,
0.086221054,
0.36541304


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
drawPieMarker(xs=[30],
              ys=[4361],
              ratios=[0,
0.701903233,
0.298096767

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
drawPieMarker(xs=[32],
              ys=[1230],
              ratios=[0,
0.426829268,
0.573170732


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
drawPieMarker(xs=[34],
              ys=[0],
              ratios=[0,
0,
0


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
plt.xticks(index + 1, ('1', '2', '3', '4','5','6','7','8','9','10','11','12','13','14','15','16','17','18','19','20','21','22','23','24','25','26','27','28','29','30','31','32','33','34','35'))
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
      
n_groups=16
index = np.arange(n_groups)

fig , ax =plt.subplots(figsize=(15,5))
#plt.figure(figsize=(15, 5))
drawPieMarker(xs=[1],
              ys=[23136],
              ratios=[0.36436722,
0.357365145,
0.278267635

],
              sizes=[500],
              colors=['white', 'grey', 'black'])
drawPieMarker(xs=[2],
              ys=[16236],
              ratios=[0.253141168,
0.294592264,
0.452266568

],
              sizes=[500],
              colors=['white', 'grey', 'black'])
drawPieMarker(xs=[3],
              ys=[11299],
              ratios=[0.363749004,
0.348083901,
0.288167094

],
              sizes=[500],
             colors=['white', 'grey', 'black'])
drawPieMarker(xs=[4],
              ys=[550],
              ratios=[0,
0,
1



],
              sizes=[500],
              colors=['white', 'grey', 'black'])
drawPieMarker(xs=[5],
              ys=[16236],
              ratios=[0.253141168,
0.294592264,
0.452266568

],
              sizes=[500],
              colors=['white', 'grey', 'black'])
drawPieMarker(xs=[6],
              ys=[10205],
              ratios=[0.402743753,
0.385399314,
0.211856933

],
              sizes=[500],
             colors=['white', 'grey', 'black'])
drawPieMarker(xs=[7],
              ys=[0],
              ratios=[0,
0,
0


],
              sizes=[500],
             colors=['white', 'grey', 'black'])
drawPieMarker(xs=[8],
              ys=[0],
              ratios=[0,
0,0


],
              sizes=[500],
               colors=['white', 'grey', 'black'])
drawPieMarker(xs=[9],
              ys=[12149],
              ratios=[0.338299449,
0.393694954,
0.268005597

],
              sizes=[500],
             colors=['white', 'grey', 'black'])
drawPieMarker(xs=[10],
              ys=[0],
              ratios=[0,
0,
0

],
              sizes=[500],
             colors=['white', 'grey', 'black'])
drawPieMarker(xs=[11],
              ys=[0],
              ratios=[0,
0,
0

],
              sizes=[500],
             colors=['white', 'grey', 'black'])
drawPieMarker(xs=[12],
             ys=[0],
              ratios=[0,
0,
0

],
              sizes=[500],
              colors=['white', 'grey', 'black'])
drawPieMarker(xs=[13],
             ys=[0],
              ratios=[0,
0,
0

],
              sizes=[500],
              colors=['white', 'grey', 'black'])


drawPieMarker(xs=[14],
             ys=[0],
              ratios=[0,
0,
0

],
              sizes=[500],
              colors=['white', 'grey', 'black'])
drawPieMarker(xs=[15],
             ys=[0],
              ratios=[0,
0,
0

],
              sizes=[500],
              colors=['white', 'grey', 'black'])
drawPieMarker(xs=[16],
             ys=[0],
              ratios=[0,
0,
0

],
              sizes=[500],
              colors=['white', 'grey', 'black'])
plt.xticks(index + 1, ('1', '2', '3', '4','5','6','7','8','9','10','11','12','13','14','15','16'))
plt.ylabel("Total distributed benefit in course type 3", fontsize=15)
plt.xlabel("Solution Number", fontsize=15)
red_patch = mpatches.Patch(color='grey', label='G1')
red_patch2 = mpatches.Patch(color='white', label='G2')
red_patch3 = mpatches.Patch(color='black', label='G3')
plt.legend(handles=[red_patch, red_patch2, red_patch3])
#plt.legend(labels=['G1','G2','G3'])
plt.savefig('CW-weightedSum-C3.png')
plt.show()







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
      
n_groups=16
index = np.arange(n_groups)

fig , ax =plt.subplots(figsize=(15,5))
#plt.figure(figsize=(15, 5))
drawPieMarker(xs=[1],
                ys=[0],
              ratios=[0,
0,
0

],
              sizes=[500],
              colors=['white', 'grey', 'black'])
drawPieMarker(xs=[2],
                ys=[0],
              ratios=[0,
0,
0

],
              sizes=[500],
              colors=['white', 'grey', 'black'])
drawPieMarker(xs=[3],
               ys=[0],
              ratios=[0,
0,
0

],
              sizes=[500],
             colors=['white', 'grey', 'black'])
drawPieMarker(xs=[4],
              ys=[0],
              ratios=[0,
0,
0

],
              sizes=[500],
              colors=['white', 'grey', 'black'])
drawPieMarker(xs=[5],
              ys=[7861],
              ratios=[0.254166137,
0.317008014,
0.428825849

],
              sizes=[500],
              colors=['white', 'grey', 'black'])
drawPieMarker(xs=[6],
              ys=[5790],
              ratios=[0.34507772,
0.367357513,
0.287564767

],
              sizes=[500],
             colors=['white', 'grey', 'black'])
drawPieMarker(xs=[7],
              ys=[5790],
              ratios=[0.34507772,
0.367357513,
0.287564767

],
              sizes=[500],
             colors=['white', 'grey', 'black'])
drawPieMarker(xs=[8],
              ys=[4985],
              ratios=[0.400802407,
0.42668004,
0.172517553

],
              sizes=[500],
               colors=['white', 'grey', 'black'])
drawPieMarker(xs=[9],
              ys=[10912],
              ratios=[0.183101173,
0.375274927,
0.4416239

],
              sizes=[500],
             colors=['white', 'grey', 'black'])
drawPieMarker(xs=[10],
              ys=[11248
],
              ratios=[0.177631579,
0.364064723,
0.458303698

],
              sizes=[500],
             colors=['white', 'grey', 'black'])
drawPieMarker(xs=[11],
              ys=[7776
],
              ratios=[0.256944444,
0.320473251,
0.422582305


],
              sizes=[500],
             colors=['white', 'grey', 'black'])
drawPieMarker(xs=[12],
             ys=[7038
],
              ratios=[0.283887468,
0.302216539,
0.413895993

],
              sizes=[500],
              colors=['white', 'grey', 'black'])
drawPieMarker(xs=[13],
             ys=[17083
],
              ratios=[0.252941521,
0.364221741,
0.382836738


],
              sizes=[500],
              colors=['white', 'grey', 'black'])


drawPieMarker(xs=[14],
             ys=[12743
],
              ratios=[0.156791964,
0.459624892,
0.383583144

],
              sizes=[500],
              colors=['white', 'grey', 'black'])
drawPieMarker(xs=[15],
             ys=[10287
],
              ratios=[0.194225722,
0.398075241,
0.407699038

],
              sizes=[500],
              colors=['white', 'grey', 'black'])
drawPieMarker(xs=[16],
             ys=[7776
],
              ratios=[0.256944444,
0.320473251,
0.422582305

],
              sizes=[500],
              colors=['white', 'grey', 'black'])
plt.xticks(index + 1, ('1', '2', '3', '4','5','6','7','8','9','10','11','12','13','14','15','16'))
plt.ylabel("Total distributed benefit in course type 2", fontsize=15)
plt.xlabel("Solution Number", fontsize=15)
red_patch = mpatches.Patch(color='grey', label='G1')
red_patch2 = mpatches.Patch(color='white', label='G2')
red_patch3 = mpatches.Patch(color='black', label='G3')
plt.legend(handles=[red_patch, red_patch2, red_patch3])
#plt.legend(labels=['G1','G2','G3'])
plt.savefig('CW-weightedSum-C2.png')
plt.show()




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
      
n_groups=16
index = np.arange(n_groups)

fig , ax =plt.subplots(figsize=(15,5))
#plt.figure(figsize=(15, 5))
drawPieMarker(xs=[1],
                ys=[0],
              ratios=[0,
0,
0

],
              sizes=[500],
              colors=['white', 'grey', 'black'])
drawPieMarker(xs=[2],
                ys=[11124
],
              ratios=[0.387720245,
0.275170802,
0.337108954

],
              sizes=[500],
              colors=['white', 'grey', 'black'])
drawPieMarker(xs=[3],
               ys=[15715
],
              ratios=[0.274451161,
0.341584473,
0.383964365

],
              sizes=[500],
             colors=['white', 'grey', 'black'])
drawPieMarker(xs=[4],
              ys=[24323
],
              ratios=[0.31459935,
0.346544423,
0.338856227

],
              sizes=[500],
              colors=['white', 'grey', 'black'])
drawPieMarker(xs=[5],
              ys=[0],
              ratios=[0,
0,
0

],
              sizes=[500],
              colors=['white', 'grey', 'black'])
drawPieMarker(xs=[6],
              ys=[8011
],
              ratios=[0.416801897,
0.382099613,
0.20109849

],
              sizes=[500],
             colors=['white', 'grey', 'black'])
drawPieMarker(xs=[7],
              ys=[16316
],
              ratios=[0.26434175,
0.361179211,
0.374479039

],
              sizes=[500],
             colors=['white', 'grey', 'black'])
drawPieMarker(xs=[8],
              ys=[17647
],
              ratios=[0.244404148,
0.477644925,
0.277950927

],
              sizes=[500],
               colors=['white', 'grey', 'black'])
drawPieMarker(xs=[9],
              ys=[0],
              ratios=[0,
0,
0

],
              sizes=[500],
             colors=['white', 'grey', 'black'])
drawPieMarker(xs=[10],
              ys=[9140

],
              ratios=[0.365317287,
0.334901532,
0.299781182


],
              sizes=[500],
             colors=['white', 'grey', 'black'])
drawPieMarker(xs=[11],
              ys=[13991

],
              ratios=[0.308269602,
0.38367522,
0.308055178

],
              sizes=[500],
             colors=['white', 'grey', 'black'])
drawPieMarker(xs=[12],
             ys=[14913
],
              ratios=[0.289210756,
0.359954402,
0.350834842

],
              sizes=[500],
              colors=['white', 'grey', 'black'])
drawPieMarker(xs=[13],
             ys=[0],
              ratios=[0,
0,
0
],
              sizes=[500],
              colors=['white', 'grey', 'black'])


drawPieMarker(xs=[14],
             ys=[7293

],
              ratios=[0.457836281,
0.419717537,
0.122446181


],
              sizes=[500],
              colors=['white', 'grey', 'black'])
drawPieMarker(xs=[15],
             ys=[10485

],
              ratios=[0.318454936,
0.342012399,
0.339532666

],
              sizes=[500],
              colors=['white', 'grey', 'black'])
drawPieMarker(xs=[16],
             ys=[13991

],
              ratios=[0.308269602,
0.38367522,
0.308055178

],
              sizes=[500],
              colors=['white', 'grey', 'black'])
plt.xticks(index + 1, ('1', '2', '3', '4','5','6','7','8','9','10','11','12','13','14','15','16'))
plt.ylabel("Total distributed benefit in course type 1", fontsize=15)
plt.xlabel("Solution Number", fontsize=15)
red_patch = mpatches.Patch(color='grey', label='G1')
red_patch2 = mpatches.Patch(color='white', label='G2')
red_patch3 = mpatches.Patch(color='black', label='G3')
plt.legend(handles=[red_patch, red_patch2, red_patch3])
#plt.legend(labels=['G1','G2','G3'])
plt.savefig('CW-weightedSum-C1.png')
plt.show()