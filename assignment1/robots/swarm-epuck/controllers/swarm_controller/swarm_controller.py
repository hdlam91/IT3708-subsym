from kd_array import *
from epuck_basic import *
from imagepro import *
from prims1 import *
from search import *
from retrieval import *


stag = False
retr = False

def search():
	#search
	distance_thresh = 200
	update_search_speed(controller.get_proximities(), distance_thresh)
	controller.move_wheels(get_search_left_wheel_speed(), get_search_right_wheel_speed(), 0.1)
	if(image_avg(controller.get_image()) > 50):
		retr = True



def retrieval():
	#retrieve
	select_behavior(controller.get_proximities())
	controller.move_wheels(get_retrieval_left_wheel_speed,get_retrieval_right_wheel_speed,0.1)
	#print "retrieve"

def stagnation():
	#stagnation
	print "stag"



controller = EpuckBasic()
controller.basic_setup()
while(True):
	#print image_avg(controller.get_image())
	#controller.move()

	if(stag):
		stagnation()
	elif(retr):
		retrieval()
	else:
		search()





#here's stuff:

#print controller.get_proximities()
	#controller.move()
	#print len(column_avg(controller.get_image()))
	#print controller.camera.getImageArray()[0]
	
	#print controller.get_image()'




	# print "searching"
	# num = 10
	# print str(sumOfArray(0, 26, column_avg(controller.get_image()))) + "left side value \n"
	# print str(sumOfArray(26, 52, column_avg(controller.get_image()))) + "right side value"
	# # if(sumOfArray(0, 25, column_avg(controller.get_image()))>sumOfArray(26, 51, column_avg(controller.get_image()))+num):
	# # 	controller.spin_left(1, 0.1)
	# # elif(sumOfArray(0, 25, column_avg(controller.get_image()))<sumOfArray(26, 51, column_avg(controller.get_image()))+num):
	# # 	controller.spin_right(1, 0.1)
	# if(column_avg(controller.get_image())[0] > column_avg(controller.get_image())[51]):
	# 	controller.spin_left(1,0.1)
	# # elif(column_avg(controller.get_image())[0] < column_avg(controller.get_image())[51]):
	# # 	controller.spin_right(1,0.1)
	# # elif(column_avg(controller.get_image())[0] == column_avg(controller.get_image())[51]):
	# # 	controller.move(speed =1, duration = 0.1, dir = 'forward')
	# else:
	# 	controller.turn_right()