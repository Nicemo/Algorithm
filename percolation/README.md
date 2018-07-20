# Algorithm
 

----------
## 1. Programming Assignment 1: Percolation
[http://coursera.cs.princeton.edu/algs4/assignments/percolation.html](http://coursera.cs.princeton.edu/algs4/assignments/percolation.html) 

物理上的逾渗模型， 也是高分子的互溶模型（笑emoij）。 



## 1.1 逾渗与否
- 逾渗成功
![逾渗成功](http://coursera.cs.princeton.edu/algs4/assignments/percolates-yes.png)
- 逾渗失败
![](http://coursera.cs.princeton.edu/algs4/assignments/percolates-no.png)
- 20 x 20 网格
![](http://coursera.cs.princeton.edu/algs4/assignments/percolation-threshold20.png)
- 100 x 100 网格
![](http://coursera.cs.princeton.edu/algs4/assignments/percolation-threshold100.png)

随着网格数的增加，逾渗概率逐渐收敛
## 1.2 模型构建
在 **n x n** 网格上定义格点类型， blocked， empty， full 三种类型， 当上下联通时即逾渗。（放弃插入公式了Orz MarkdownPad 一直显示不了）

**蒙特卡洛模拟**

- 将所有网格点重置blocked。
- While (！Percolation) {随机打开一个bolcked的格点}
- 预估逾渗的临界点




![](http://coursera.cs.princeton.edu/algs4/assignments/percolation-50.png) ![](http://coursera.cs.princeton.edu/algs4/assignments/percolation-100.png)

![](http://coursera.cs.princeton.edu/algs4/assignments/percolation-150.png) ![](http://coursera.cs.princeton.edu/algs4/assignments/percolation-204.png)

## 1.3 实例
主要调用WeightedQuickUnion类
