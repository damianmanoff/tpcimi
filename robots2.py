from pybrain.datasets import SupervisedDataSet
from pybrain.supervised.trainers import BackpropTrainer
from pybrain.tools.shortcuts import buildNetwork
from pybrain.structure.modules import TanhLayer
import csv

ds = SupervisedDataSet(2, 1)
tf = open('data/train_format1.csv','r')
csvfile2 	= open('results.csv','w')

tf.readline()

print "Reading file"
for line in tf.readlines():
    data = [float(x) for x in line.strip().split(',') if x != '']
    indata =  tuple(data[:2])
    outdata = tuple(data[2:])
    ds.addSample(indata,outdata)

net     = buildNetwork(2, 3, 1, bias=True, hiddenclass=TanhLayer)
trainer = BackpropTrainer(net, ds)

print "Trainning"


errors  = trainer.trainUntilConvergence(ds, maxEpochs=2)
print "Finish Trainning"

print errors

spamwriter = csv.writer(csvfile2, delimiter=' ', quotechar='|', quoting=csv.QUOTE_MINIMAL)
for inp, tar in ds:
	spamwriter.writerow([inp, net.activate(inp)])

'''
x


for inp, tar in ds:
     print [net.activate(inp), tar]'''