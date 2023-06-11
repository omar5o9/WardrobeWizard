Project repo for CSCD 350

Description: This project is an application software that serves the function of a virtual closet, tentatively named ‘Wardrobe Wizard’. It will be able 
to store a list of whatever articles of clothing a user owns, and once the user uploads images of their clothing the images and descriptions of their 
clothing will be stored in their personal virtual closet. The app will then allow you to enter information about your clothes such as brand, price, size, 
and color. In addition, we will be able to sort clothing based on these attributes. ‘Wardrobe Wizard’ will support two major features: an outfit system 
and a laundry basket system. The outfit system will allow users to make sets of clothing that they frequently wear together, and store these sets to their 
profile. The laundry basket system will allow users to mark clothing as previously worn so they remember not to wear it again, and provide statistics of 
how often they wear a piece of clothing.

Team: \
Back-end: Omar Facundo and Cameron Zamora \
Front end: Beighlor Martinez and Roberto Suarez \

How to obtain the source code: Run 'git clone https://github.com/omar5o9/WardrobeWizard' in your favorite command line interface

Directory Structure: \
> WardrobeWizard/                                       --                  The parent directory 
  >> app/src/ \
  >> gradle/wrapper                                   --                            Gradle dependencies are kept here \
        >>> main/ \
          >>>> java/com/example/wardrobewizard  --                             This is where all the java files are kept \
          >>>> test/java/com/example/wardrobewizard       --                   This is where unit tests for the java files are kept  \ 
          >>>> res/ \
              >>>>> layout                        --                            This is where all the xml files for the pages of the app are kept \
              >>>>> drawable                        --                          This is where all the image files for the app's pages are kept \
              >>>>> mipmap-anydpi-v26 \
              >>>>> mipmap-hdpi \
              >>>>> mipmap-mdpi \
              >>>>> mipmap-xhdpi \
              >>>>> mipmap-xxhdpi \
              >>>>> mipmap-xxxhdpi \
              >>>>> menu \
              >>>>> navigation \
              >>>>> values-night \
              >>>>> values \
              >>>>> xml 
    
How to build the software: In Android Studio, click 'File', then 'New', then 'Import Project'. In the window that appears, navigate to the root directory 
of the project you want to import. Click 'OK'. Finally, click the 'Build' icon in the toolbar.

How to run the software: In the toolbar, select your app from the run configurations menu. In the target device menu, select the Pixel 3 virtual machine. 
Click 'run'.

How to test the software: Run the software. For each page you wish to test, visit that page in the virtual machine. To add new unit tests, save them to: 
WardrobeWizard/app/src/test/java/com/example/wardrobewizard. The filenames are to be written in PascalCase.
