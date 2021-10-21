# TIFF-Reader
Create a program that reads an uncompressed TIFF image file and display it on the screen. A brief introduction and historical review of TIFF can be found at https://en.wikipedia.org/wiki/TIFF
Details about the TIFF 6.0 format can be found in TIFF6.pdf as well as many other places online.
Only the 24-bit RGB full color uncompressed mode in TIFF will be considered (see Section 6: RGB Full Color Images in TIFF6.pdf). You can assume that the image is no bigger than the 4*CIF size (i.e., 704*576). You can ignore header tags that are not used in this project.
Your program should first show an open file dialog box for the user to select the TIFF file. It should then work as follows:
1. Displays the original colored image;
2. Refreshes by the corresponding grayscale image of the original image;
3. Refreshes by applying ordered dithering on the grayscale image;
4. Refreshes by making the brighter part of the image darker and the darker part brighter, which is known as “adjusting the dynamic range” in photo editing.
To move to the next step, your can either place a “next” button on screen, or pause until any key is pressed. Go back to step 1 after step 4. There should also be a “quit” button.
We will not specify any dither matrix. You can experiment different ones and choose a good one. You need to discuss your choice of the dither matrix in the report. For adjustment of dynamic range, you should experiment different ways/thresholds and discuss the effects, too.
Grading scheme
1. File input (2 marks)
2. Display the color and grayscale images (4 marks each)
3. Display the dithered image and discuss about the dither matrix (5 marks)
4. Dynamic range adjustment and discuss about your solutions/experiments (4 marks)

Rationale of doing this project:
1. Learn a media file format and how is it specified. You will find that there are so many fine details. There are so many different formats in industry (see for example Murray, James D.; vanRyper, William (April 1996). "Encyclopedia of Graphics File Formats" (Second ed.). O'Reilly. ISBN 1-56592-161-5. ) Yet as long as you have experience with one format, you’ll find that you can easily hack most others. In fact, TIFF is known as the “base” or “origin” of many other formats.
2. Have some inside knowledge about some Photoshop operations, e.g., remove color, adjust dynamic range. We will see more in next project. You will then find that you know Photoshop (or other similar tools) much better, and you can indeed write your own.
3. Have some taste about research. Say for the choice of dither matrix and dynamic range adjustment, we expect that you find a good one (or the best trade-off) through your own design/experiments, while not simply and passively look for an answer from textbook. The discussion part will be the highlight of your report, which will showcase your own hard works and distinguish yourself from others. (same for Programming assignment)