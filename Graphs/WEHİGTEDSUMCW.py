# -*- coding: utf-8 -*-
"""
Created on Sun Mar 14 22:39:50 2021

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
      
n_groups=15
index = np.arange(n_groups)

fig , ax =plt.subplots(figsize=(15,5))
#plt.figure(figsize=(15, 5))
drawPieMarker(xs=[1],
              ys=[23136
],
              ratios=[0.36436722,
0.357365145,
0.278267635

],
              sizes=[500],
              colors=['white', 'grey', 'black'])
drawPieMarker(xs=[2],
              ys=[18267
],
              ratios=[0.224995894,
0.452619478,
0.322384628

],
              sizes=[500],
              colors=['white', 'grey', 'black'])
drawPieMarker(xs=[3],
              ys=[14782
],
              ratios=[0.278040861,
0.323569206,
0.398389934

],
              sizes=[500],
             colors=['white', 'grey', 'black'])
drawPieMarker(xs=[4],
              ys=[10001
],
              ratios=[0.410958904,
0.393260674,
0.195780422

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
              ys=[18267
],
              ratios=[0.224995894,
0.452619478,
0.322384628

],
              sizes=[500],
             colors=['white', 'grey', 'black'])
drawPieMarker(xs=[7],
              ys=[12353
],
              ratios=[0.332712701,
0.387193394,
0.280093904

],
              sizes=[500],
             colors=['white', 'grey', 'black'])
drawPieMarker(xs=[8],
              ys=[6918
],
              ratios=[0.594102342,
0.122867881,
0.283029777

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
              ys=[14782
],
              ratios=[0.278040861,
0.323569206,
0.398389934


],
              sizes=[500],
             colors=['white', 'grey', 'black'])
drawPieMarker(xs=[11],
              ys=[6918
],
              ratios=[0.594102342,
0.122867881,
0.283029777


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
             ys=[6918
],
              ratios=[0.594102342,
0.122867881,
0.283029777

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

plt.xticks(index + 1, ('1', '2', '3', '4','5','6','7','8','9','10','11','12','13','14','15'))
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
      
n_groups=15
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
                ys=[5647
],
              ratios=[0.353816186,
0.376660174,
0.269523641

],
              sizes=[500],
              colors=['white', 'grey', 'black'])
drawPieMarker(xs=[3],
               ys=[9526
],
              ratios=[0.209741759,
0.261599832,
0.528658409

],
              sizes=[500],
             colors=['white', 'grey', 'black'])
drawPieMarker(xs=[4],
              ys=[12049
],
              ratios=[0.16582289,
0.486098431,
0.348078679

],
              sizes=[500],
              colors=['white', 'grey', 'black'])
drawPieMarker(xs=[5],
              ys=[17083
],
              ratios=[0.252941521,
0.364221741,
0.382836738

],
              sizes=[500],
              colors=['white', 'grey', 'black'])
drawPieMarker(xs=[6],
              ys=[0],
              ratios=[0,
0,
0

],
              sizes=[500],
             colors=['white', 'grey', 'black'])
drawPieMarker(xs=[7],
              ys=[5035
],
              ratios=[0.396822244,
0.4224429,
0.180734856

],
              sizes=[500],
             colors=['white', 'grey', 'black'])
drawPieMarker(xs=[8],
              ys=[7763
],
              ratios=[0.257374726,
0.321009919,
0.421615355

],
              sizes=[500],
               colors=['white', 'grey', 'black'])
drawPieMarker(xs=[9],
              ys=[12743
],
              ratios=[0.156791964,
0.459624892,
0.383583144


],
              sizes=[500],
             colors=['white', 'grey', 'black'])
drawPieMarker(xs=[10],
              ys=[0
],
              ratios=[0,
0,
0

],
              sizes=[500],
             colors=['white', 'grey', 'black'])
drawPieMarker(xs=[11],
              ys=[4985

],
              ratios=[0.400802407,
0.42668004,
0.172517553


],
              sizes=[500],
             colors=['white', 'grey', 'black'])
drawPieMarker(xs=[12],
             ys=[7776

],
              ratios=[0.256944444,
0.320473251,
0.422582305

],
              sizes=[500],
              colors=['white', 'grey', 'black'])
drawPieMarker(xs=[13],
             ys=[0
],
              ratios=[0,
0,
0


],
              sizes=[500],
              colors=['white', 'grey', 'black'])


drawPieMarker(xs=[14],
             ys=[4985

],
              ratios=[0.400802407,
0.42668004,
0.172517553


],
              sizes=[500],
              colors=['white', 'grey', 'black'])
drawPieMarker(xs=[15],
              ys=[0
],
              ratios=[0,
0,
0


],
              sizes=[500],
              colors=['white', 'grey', 'black'])

plt.xticks(index + 1, ('1', '2', '3', '4','5','6','7','8','9','10','11','12','13','14','15'))
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
      
n_groups=15
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
              ys=[0],
              ratios=[0,
0,
0

],
              sizes=[500],
              colors=['white', 'grey', 'black'])
drawPieMarker(xs=[6],
              ys=[8260

],
              ratios=[0.404237288,
0.370581114,
0.225181598

],
              sizes=[500],
             colors=['white', 'grey', 'black'])
drawPieMarker(xs=[7],
              ys=[7529

],
              ratios=[0.443485191,
0.406561296,
0.149953513

],
              sizes=[500],
             colors=['white', 'grey', 'black'])
drawPieMarker(xs=[8],
              ys=[8072

],
              ratios=[0.413652131,
0.379212091,
0.207135778


],
              sizes=[500],
               colors=['white', 'grey', 'black'])
drawPieMarker(xs=[9],
              ys=[7293
],
              ratios=[0.457836281,
0.419717537,
0.122446181


],
              sizes=[500],
             colors=['white', 'grey', 'black'])
drawPieMarker(xs=[10],
              ys=[13131


],
              ratios=[0.328459371,
0.273094205,
0.398446424


],
              sizes=[500],
             colors=['white', 'grey', 'black'])
drawPieMarker(xs=[11],
              ys=[11649


],
              ratios=[0.370246373,
0.307837583,
0.321916044

],
              sizes=[500],
             colors=['white', 'grey', 'black'])
drawPieMarker(xs=[12],
             ys=[13991

],
              ratios=[0.308269602,
0.38367522,
0.308055178

],
              sizes=[500],
              colors=['white', 'grey', 'black'])
drawPieMarker(xs=[13],
             ys=[19200
],
              ratios=[0.224635417,
0.439010417,
0.336354167

],
              sizes=[500],
              colors=['white', 'grey', 'black'])


drawPieMarker(xs=[14],
             ys=[17647


],
              ratios=[0.244404148,
0.477644925,
0.277950927


],
              sizes=[500],
              colors=['white', 'grey', 'black'])
drawPieMarker(xs=[15],
             ys=[24323


],
              ratios=[0.31459935,
0.346544423,
0.338856227

],
              sizes=[500],
              colors=['white', 'grey', 'black'])

plt.xticks(index + 1, ('1', '2', '3', '4','5','6','7','8','9','10','11','12','13','14','15'))
plt.ylabel("Total distributed benefit in course type 1", fontsize=15)
plt.xlabel("Solution Number", fontsize=15)
red_patch = mpatches.Patch(color='grey', label='G1')
red_patch2 = mpatches.Patch(color='white', label='G2')
red_patch3 = mpatches.Patch(color='black', label='G3')
plt.legend(handles=[red_patch, red_patch2, red_patch3])
#plt.legend(labels=['G1','G2','G3'])
plt.savefig('CW-weightedSum-C1.png')
plt.show()