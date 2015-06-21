from pybrain.datasets import SupervisedDataSet
from pybrain.supervised.trainers import BackpropTrainer
from pybrain.tools.shortcuts import buildNetwork
from pybrain.structure.modules import TanhLayer
import pickle
import csv

ds = SupervisedDataSet(9, 1)
tf = open('data/train_format2P1.csv','r')
tf2 = open('data/test_format2P1.csv','r')
csvfile2 	= open('results2.csv','w')

tf.readline()

print "Reading file"
for line in tf.readlines():
    data = [float(x) for x in line.strip().split(',') if x != '']
    indata =  tuple(data[:8] + [data[9]])
    outdata = tuple(data[9:])
    ds.addSample(indata,outdata)

net     = buildNetwork(9, 3, 1, bias=True, hiddenclass=TanhLayer)
trainer = BackpropTrainer(net, ds, learningrate = 0.001, momentum = 0.99)

print "Trainning"


errors  = trainer.train()
errors  = trainer.trainUntilConvergence(verbose=True, maxEpochs=1)
errors  = trainer.train()

fileObject = open('netWork.net', 'w')

pickle.dump(net, fileObject)

print "Finish Trainning"

print errors

spamwriter = csv.writer(csvfile2, delimiter=',', quotechar=',', quoting=csv.QUOTE_MINIMAL)

for line in tf2.readlines():
	data = [float(x) for x in line.strip().split(',') if x != '']
	spamwriter.writerow([data[:9], net.activate(data[:9])[0]])



'''

from pybrain.datasets import SupervisedDataSet
from pybrain.supervised.trainers import BackpropTrainer
from pybrain.tools.shortcuts import buildNetwork
from pybrain.structure.modules import TanhLayer
import pickle
import csv

ds = SupervisedDataSet(2, 1)
tf = open('data/train_format2P1.csv','r')
tf2 = open('data/test_format2P1.csv','r')
csvfile2 	= open('results2.csv','w')

tf.readline()

print "Reading file"
for line in tf.readlines():
    data = [float(x) for x in line.strip().split(',') if x != '']
    indata =  tuple([data[0],data[3]])
    outdata = tuple(data[9:])
    ds.addSample(indata,outdata)

net     = buildNetwork(2, 3, 1, bias=True, hiddenclass=TanhLayer)
trainer = BackpropTrainer(net, ds, learningrate = 0.001, momentum = 0.99)

print "Trainning"


errors  = trainer.train()
errors  = trainer.trainUntilConvergence(verbose=True, maxEpochs=1)
errors  = trainer.train()

fileObject = open('netWork.net', 'w')

pickle.dump(net, fileObject)

fileObject.close()

print "Finish Trainning"

print errors

spamwriter = csv.writer(csvfile2, delimiter=',', quotechar=',', quoting=csv.QUOTE_MINIMAL)

for line in tf2.readlines():
	data = [float(x) for x in line.strip().split(',') if x != '']
	spamwriter.writerow([data[0] + data[3], net.activate([data[0],data[3]])[0]])


'''
