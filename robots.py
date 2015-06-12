from pybrain.structure import FeedForwardNetwork
from pybrain.structure import FullConnection
from pybrain.structure import LinearLayer, SigmoidLayer
from pybrain.supervised.trainers import BackpropTrainer
from pybrain.datasets import SupervisedDataSet
from pybrain.utilities import percentError
import csv

n = FeedForwardNetwork()

inLayer = LinearLayer(2)
hiddenLayer = SigmoidLayer(2)
outLayer = LinearLayer(1)

n.addInputModule(inLayer)
n.addModule(hiddenLayer)
n.addOutputModule(outLayer)


in_to_hidden = FullConnection(inLayer, hiddenLayer)
hidden_to_out = FullConnection(hiddenLayer, outLayer)
n.addConnection(in_to_hidden)
n.addConnection(hidden_to_out)
n.sortModules()

ds = SupervisedDataSet(2,1)

tf = open('data/train_format1.csv','r')

tf.readline()

print "Reading file"
for line in tf.readlines():
    data = [float(x) for x in line.strip().split(',') if x != '']
    indata =  tuple(data[:2])
    outdata = tuple(data[2:])
    ds.addSample(indata,outdata)

print "Trainning dataset"
st = BackpropTrainer(n,learningrate=0.01,momentum=0.5,verbose=True)
st.trainUntilConvergence(ds)
st.testOnData(verbose=True)
