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
	#print "search"
	global retr
	distance_thresh = 200
	
	
	imageAvg = imageArrayAvg(controller.camera.getImageArray())
	left = imageAvg[0:5]
	right = imageAvg[len(imageAvg)-5:len(imageAvg)]
	colorThresh = 200
	distances = controller.get_proximities()
	
	#print str(sum(left)/len(left))+ "+" + str(sum(right)/len(right))

	#print imageAvg


	if(sum(left)/len(left)>colorThresh):
		if(sum(right)/len(right)>colorThresh):
			##this makes them converge after a while
			# if(distances[0] > 500 and distances[7] >500):
			# 	if(distances[0]>distances[7]):
			# 		controller.spin_right(0.5,0.1)
			# 	elif(distances[0]<distances[7]):
			# 		controller.spin_left(0.5,0.1)
			# 	else:
			# 		update_search_speed(controller.get_proximities(), distance_thresh)
			# 		controller.move_wheels(get_search_left_wheel_speed(), get_search_right_wheel_speed(), 0.1)
			# else:
			retr = True
			controller.led[8].set(1)
		else:
			controller.spin_left(1,0.1)
	elif(sum(right)/len(right)>colorThresh):
		controller.spin_right(1,0.1)
	else:
		update_search_speed(controller.get_proximities(), distance_thresh)
		controller.move_wheels(get_search_left_wheel_speed(), get_search_right_wheel_speed(), 0.1)
	




def retrieval():
	image = controller.get_image()
	#left = imageAvg[0:len(imageAvg)/2]
	#right = imageAvg[len(imageAvg)/2:len(imageAvg)]
	colorThresh = 200
	distances = controller.get_proximities()
	
	if(image_avg(image) < colorThresh):
		retr = False
		
	irThresh = 100
	distances = controller.get_proximities()
	select_behavior(distances)
	swarm_retrieval(distances, irThresh)
	controller.move_wheels(get_retrieval_left_wheel_speed, get_retrieval_right_wheel_speed(), 0.1)

def stagnation():
	#stagnation
	print "stag"



controller = EpuckBasic()
controller.basic_setup()
controller.move(0.1)
#for i in range(0,2):
while(True):
	#print column_avg(controller.get_image())[0]
	#controller.move()
	#print get_red_image(controller.get_image())
	#print controller.camera.getImageArray()[0]
	#print imageArrayAvg(controller.camera.getImageArray())
	# if(stag):
	# 	stagnation()
	# elif(retr):
	# 	retrieval()
	# else:
	search()

controller.stop_moving()



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