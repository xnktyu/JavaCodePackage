#!/usr/bin/python3

import sys
import time
import pyautogui

from PIL import ImageGrab

imgDir = "D:/dang/python/test/img"
imgLoadFailMark = "%s/%s.bmp" % (imgDir, "loadFailMark")
imgLoginPassMark = "%s/%s.bmp" % (imgDir, "loginPassMark")

bookDir = "D:/dang/python/test/深入理解Android内核设计思想(第2版)(上下册)"
bookDir2 = "D:/dang/python/test/深入理解Android内核设计思想(第2版)(上下册)bmp"
pageCount = 2724


def marchMark(fileMark):
	return pyautogui.locateCenterOnScreen(fileMark, grayscale=True)


def catchImage(area, file):
	im = ImageGrab.grab(bbox=area)
	im.save(file)


def checkMark():
	markResult = marchMark(imgLoadFailMark)
	if markResult:
		print("imgLoadFailMark", markResult)
		pyautogui.click(markResult)
		time.sleep(1)
		return checkMark()
	markResult = marchMark(imgLoginPassMark)
	if markResult:
		print("imgLoginPassMark", markResult)
		pyautogui.moveTo(markResult)
		return False
	return True


def catchPage(i):
	if checkMark():
		#print("catch", i)
		catchImage((354, 72, 1012, 950), "%s/%d.jpg" % (bookDir, i))
		catchImage((354, 72, 1012, 950), "%s/%d.bmp" % (bookDir2, i))
		return True
	else:
		return False


def startImpl(i):
	while i <= pageCount:
		if catchPage(i):
			pyautogui.press('right')
			#time.sleep(1)
			i += 1
		else:
			break
	else:
		return True
	return False


def start(i):
	if startImpl(i):
		print("catch success")
	else:
		print("catch fail")


def catchMark(area, fileMark):
	catchImage(area, fileMark)
	markResult = marchMark(fileMark)
	if markResult:
		print("markResult", markResult)
		pyautogui.moveTo(markResult)


print("ready...")
time.sleep(3)
print("go")

start(320)
# catchPage(xxx)

#catchMark((358, 448, 440, 448 + 1), imgLoadFailMark)
#catchMark((666, 510, 856, 510 + 1), imgLoginPassMark)

# im = pyautogui.screenshot(bookDir + "/1.jpg", region=(0, 0, 300, 400))
