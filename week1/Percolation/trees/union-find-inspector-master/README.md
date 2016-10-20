union-find-inspector
====================

A tree visualizer for inspecting Disjoint-Set (or Union-Find) data structure.

![A disjoint-set data structure representation using a sunray tree visualization](/images/union-find-sunray-400.jpg "Sunray Tree")

## Why? ##

First for curiousity, for seeing in a human-friendly way how a tree built from a disjoint-set looks like. The array data upon which it relies isn't helpful for understanding its structure.

Second, to help any people out there who would want to modify the algorithm to their requirements. 

## Usage ##

You need Maven to build and execute the project. [Download, install it](http://maven.apache.org/) and ensure the Maven binaries are available in your path (via the `PATH` environment variable for example). Once done, change directory in the root of this cloned project and execute:

```
mvn package
```

Then, change directory in the newly created *target* directory and execute the *union-find-inspector-1.0.jar* file. You can either double-click the *jar* file in Windows or via the command-line:

```
java -jar union-find-inspector-1.0.jar
```

## File Formats ##

The visualizer supports two file formats to represent a disjoint-set data structure: a percolation entries file and an array of *IDs *file.

### Percolation File (*.perc) ###

The percolation file contains the dimension and union instructions for the cell joins. It follows exactly the same format as the *Algorithms, Part 1* Coursera class:

```
5
  1   2
  1   3
  3   1
  5   1
  2   3
  3   4
  4   1
  5   5
  4   5
  2   2
  3   3
  4   3
  5   3
```

The first line contains an integer that informs of the cell table dimension. In this case above, this would be a 5X5 table. 

The following lines contains two coordinates on every line that indicates a union operation on the specified cells. The first coordinate is the X axis (horizontal) and the second one the Y axis (vertical).

### IDs File (*.uf) ###

This file should have one line. The line should be a copy/paste of the Java debugger output of the disjoint-set array. It should look as the following:

```
[0,1,0,0,4,5,6,0,0,9,10,26,12,0,0,15,11,17,0,19,26,26,22,26,24,26,0]
```

## Examples ##

### Hyperbolic Tree ###

![A disjoint-set data representation using a hyperbolic tree visualization](/images/union-find-hypertree-25.jpg "Hyperbolic Tree")

### Treemap ###

![A disjoint-set data representation using a treemap visualization](/images/union-find-treemap-25.jpg "Treemap")

### Sunray Tree ###

![A disjoint-set data representation using a sunray tree visualization](/images/union-find-sunray-25.jpg "Sunray Tree")

### Misc ###

Labels pop-ups will show the node hierarchy in the trees:

![Labels](/images/union-find-treemap-25-label.jpg "Labels")

## Coursera Students ##

You can plug-in your `Percolation` implementation into this visualizer, but it will need a few modifications. The modifications to perform:

1. Implement the `IPercolation` interface.
1. Change the package signature of your `Percolation` class from the default to `org.jlr.percolation`.
1. Name the `WeightedQuickUnionUF` class member to `percolated`.
 
Some of these restrictions could be eased or removed if there is demand. <:o)
