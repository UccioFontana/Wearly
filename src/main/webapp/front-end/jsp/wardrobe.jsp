<%--
  Created by IntelliJ IDEA.
  User: ucciofontana
  Date: 20/01/25
  Time: 20:58
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Wardrobe Page</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/front-end/css/wardrobe.css">
</head>
<body>
<header>
  <div id="logoContainer">
    <img id="logo" src="${pageContext.request.contextPath}/images/Wearly_Logo.png" onclick="window.location.href = ('${pageContext.request.contextPath}/home')">
  </div>
  <div id="spaceFiller">

  </div>
  <div id="buttonsContainer">
    <button class="defaultButtons" onclick="window.location.href = ('${pageContext.request.contextPath}/authservlet')">Sign In/Up</button>
    <svg width="50px" height="50px" viewBox="0 0 24 24" fill="none" xmlns="http://www.w3.org/2000/svg"><g id="SVGRepo_bgCarrier" stroke-width="0"></g><g id="SVGRepo_tracerCarrier" stroke-linecap="round" stroke-linejoin="round"></g><g id="SVGRepo_iconCarrier"> <path fill-rule="evenodd" clip-rule="evenodd" d="M12 19.5C16.1421 19.5 19.5 16.1421 19.5 12C19.5 7.85786 16.1421 4.5 12 4.5C7.85786 4.5 4.5 7.85786 4.5 12C4.5 16.1421 7.85786 19.5 12 19.5ZM12 21C16.9706 21 21 16.9706 21 12C21 7.02944 16.9706 3 12 3C7.02944 3 3 7.02944 3 12C3 16.9706 7.02944 21 12 21ZM12.75 15V16.5H11.25V15H12.75ZM10.5 10.4318C10.5 9.66263 11.1497 9 12 9C12.8503 9 13.5 9.66263 13.5 10.4318C13.5 10.739 13.3151 11.1031 12.9076 11.5159C12.5126 11.9161 12.0104 12.2593 11.5928 12.5292L11.25 12.7509V14.25H12.75V13.5623C13.1312 13.303 13.5828 12.9671 13.9752 12.5696C14.4818 12.0564 15 11.3296 15 10.4318C15 8.79103 13.6349 7.5 12 7.5C10.3651 7.5 9 8.79103 9 10.4318H10.5Z" fill="#30503d"></path> </g></svg>
    <svg onclick="window.location.href = ('toWardrobe')" width="42px" height="42px" viewBox="0 0 16 16" fill="none" xmlns="http://www.w3.org/2000/svg" stroke="#333333" stroke-width="0.00016"><g id="SVGRepo_bgCarrier" stroke-width="0"></g><g id="SVGRepo_tracerCarrier" stroke-linecap="round" stroke-linejoin="round"></g><g id="SVGRepo_iconCarrier"> <path fill-rule="evenodd" clip-rule="evenodd" d="M5 4.5C5 2.567 6.567 1 8.5 1C10.433 1 12 2.567 12 4.5V4.55376C12 5.82905 11.3167 7.00657 10.2094 7.63929L10.0156 7.75005L16 11.1697V15.0001H0V11.1697L9.21712 5.9028C9.70123 5.62617 10 5.11134 10 4.55376V4.5C10 3.67157 9.32843 3 8.5 3C7.67157 3 7 3.67157 7 4.5V5H5V4.5ZM8 8.9018L2 12.3304V13.0001H14V12.3304L8 8.9018Z" fill="#333333"></path> </g></svg>
  </div>
</header>

  <div id="fascinationContainer">
    <h1 id="fascination">Your wardrobe just got a <span id="fascinationSpan">personal assistant.</span></h1>
    <div style="display: flex; justify-content: center; gap: 1vw">
      <button class="addItemButton">Add New Item</button>
      <button class="addItemButton">Generate Outfit ✨</button>
    </div>
  </div>
  <div id="outerWaveContainer">
    <div id="waveContainer"></div>
      <svg id="wave" xmlns="http://www.w3.org/2000/svg" xmlns:xlink="http://www.w3.org/1999/xlink" preserveAspectRatio="xMidYMid meet" version="1.0" viewBox="-0.2 -4.4 466.0 60.6" zoomAndPan="magnify" style="fill: rgb(1, 118, 129);" original_string_length="635"><g id="__id0_shh9k08pmz"><path d="M369.54,55.44c-35.04,0-70.06-4.9-104-14.7l-76.93-22.2C132.61,2.39,72.76-0.33,15.53,10.7L2.38,13.23 c-1.08,0.21-2.13-0.5-2.34-1.59c-0.21-1.08,0.5-2.13,1.59-2.34l13.15-2.53c57.85-11.15,118.35-8.4,174.95,7.93l76.93,22.2 c64.03,18.48,131.95,19.36,196.44,2.56c1.07-0.28,2.16,0.36,2.44,1.43s-0.36,2.16-1.43,2.44C433.1,51.41,401.31,55.44,369.54,55.44z" style="fill: #017681;"/></g></svg>
      <div id="searchBarContainer">
        <h1 id="searchHeading">Search</h1>
        <input id="searchBar" type="text" placeholder="Search Something">
      </div>
  </div>

  <div id="outerItemsContainer">
    <div id="innerItemsContainer">

        <div class="col-md-3">
          <div class="wsk-cp-product">
            <div class="wsk-cp-img">
              <img src="${pageContext.request.contextPath}/images/3DB0DB89-7F7D-47D2-8B25-AD71FADC9D71.JPG" alt="Product" class="img-responsive"  height="330px"/>
            </div>
            <div class="wsk-cp-text">
              <div class="category">
                <span style="font-size: 0.5rem;">Ethnic</span>
              </div>
              <div class="title-product">
                <h3 style="font-size: 0.8rem;">My face not my heart</h3>
              </div>
              <div class="description-prod">
                <p style="font-size: 0.8rem;">Description Product tell me how to change playlist height size like 600px in html5 player. player good work now check this link</p>
              </div>
              <div class="card-footer">
                <div class="wcf-left"><span class="price">Estate</span></div>
                <div class="wcf-right"><a href="#" class="buy-btn"><i class="zmdi zmdi-shopping-basket"></i></a></div>
              </div>
            </div>
          </div>
        </div>

        <div class="col-md-3">
          <div class="wsk-cp-product">
            <div class="wsk-cp-img">
              <img src="${pageContext.request.contextPath}/images/banner1.jpg" alt="Product" class="img-responsive"  height="330px"/>
            </div>
            <div class="wsk-cp-text">
              <div class="category">
                <span style="font-size: 0.5rem;">Ethnic</span>
              </div>
              <div class="title-product">
                <h3 style="font-size: 0.8rem;">My face not my heart</h3>
              </div>
              <div class="description-prod">
                <p style="font-size: 0.8rem;">Description Product tell me how to change playlist height size like 600px in html5 player. player good work now check this link</p>
              </div>
              <div class="card-footer">
                <div class="wcf-left"><span class="price">Estate</span></div>
                <div class="wcf-right"><a href="#" class="buy-btn"><i class="zmdi zmdi-shopping-basket"></i></a></div>
              </div>
            </div>
          </div>
        </div>



    </div>
  </div>

  <div id="popupOverlay" class="overlay"></div>
  <div id="popup" class="popup">

    <div id="outfitSelectionContainer">
      <div style="z-index: 2000">
        <button class="closeButton" onclick="closePopup()">✖</button>
      </div>
      <!-- Outfit 1 -->
      <div class="outfitRow">
        <div class="content">
          <h2>Outfit 1</h2>
          <div class="categoryRow">
            <div class="category">
              <h3>Top</h3>
              <img src="top1.jpg" alt="Top 1">
            </div>
            <div class="category">
              <h3>Bottom</h3>
              <img src="bottom1.jpg" alt="Bottom 1">
            </div>
            <div class="category">
              <h3>Shoes</h3>
              <img src="shoes1.jpg" alt="Shoes 1">
            </div>
          </div>
        </div>
        <button class="selectButton" style="margin-top: 5vw">Select</button>
      </div>

      <!-- Outfit 2 -->
      <div class="outfitRow">
        <div class="content">
          <h2>Outfit 2</h2>
          <div class="categoryRow">
            <div class="category">
              <h3>Top</h3>
              <img src="top2.jpg" alt="Top 2">
            </div>
            <div class="category">
              <h3>Bottom</h3>
              <img src="bottom2.jpg" alt="Bottom 2">
            </div>
            <div class="category">
              <h3>Shoes</h3>
              <img src="shoes2.jpg" alt="Shoes 2">
            </div>
          </div>
        </div>
        <button class="selectButton" style="margin-top: 5vw">Select</button>
      </div>

      <!-- Outfit 3 -->
      <div class="outfitRow">
        <div class="content">
          <h2>Outfit 3</h2>
          <div class="categoryRow">
            <div class="category">
              <h3>Top</h3>
              <img src="top3.jpg" alt="Top 3">
            </div>
            <div class="category">
              <h3>Bottom</h3>
              <img src="bottom3.jpg" alt="Bottom 3">
            </div>
            <div class="category">
              <h3>Shoes</h3>
              <img src="shoes3.jpg" alt="Shoes 3">
            </div>
          </div>
        </div>
        <button class="selectButton" style="margin-top: 5vw">Select</button>
      </div>

      <!-- Input per Nome e Descrizione -->
      <div class="outfitDetails">
        <input  class="outfitForms" type="text" placeholder="Outfit Name">
        <textarea class="outfitForms" placeholder="Outfit Description"></textarea>
      </div>

      <!-- Pulsante "Salva Outfit" -->
      <button class="saveButton" style="margin-top: 2vw">Save Outfit</button>
    </div>
  </div>


  <script src="${pageContext.request.contextPath}/front-end/js/wardrobe.js"></script>
</body>
</html>
