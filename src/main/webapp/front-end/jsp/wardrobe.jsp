<%@ page import="java.util.List" %>
<%@ page import="model.CapoAbbigliamento" %><%--
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
    <svg onclick="window.location.href = ('toSupportPage')" width="50px" height="50px" viewBox="0 0 24 24" fill="none" xmlns="http://www.w3.org/2000/svg"><g id="SVGRepo_bgCarrier" stroke-width="0"></g><g id="SVGRepo_tracerCarrier" stroke-linecap="round" stroke-linejoin="round"></g><g id="SVGRepo_iconCarrier"> <path fill-rule="evenodd" clip-rule="evenodd" d="M12 19.5C16.1421 19.5 19.5 16.1421 19.5 12C19.5 7.85786 16.1421 4.5 12 4.5C7.85786 4.5 4.5 7.85786 4.5 12C4.5 16.1421 7.85786 19.5 12 19.5ZM12 21C16.9706 21 21 16.9706 21 12C21 7.02944 16.9706 3 12 3C7.02944 3 3 7.02944 3 12C3 16.9706 7.02944 21 12 21ZM12.75 15V16.5H11.25V15H12.75ZM10.5 10.4318C10.5 9.66263 11.1497 9 12 9C12.8503 9 13.5 9.66263 13.5 10.4318C13.5 10.739 13.3151 11.1031 12.9076 11.5159C12.5126 11.9161 12.0104 12.2593 11.5928 12.5292L11.25 12.7509V14.25H12.75V13.5623C13.1312 13.303 13.5828 12.9671 13.9752 12.5696C14.4818 12.0564 15 11.3296 15 10.4318C15 8.79103 13.6349 7.5 12 7.5C10.3651 7.5 9 8.79103 9 10.4318H10.5Z" fill="#30503d"></path> </g></svg>
    <svg onclick="window.location.href = ('toWardrobe')" width="42px" height="42px" viewBox="0 0 16 16" fill="none" xmlns="http://www.w3.org/2000/svg" stroke="#333333" stroke-width="0.00016"><g id="SVGRepo_bgCarrier" stroke-width="0"></g><g id="SVGRepo_tracerCarrier" stroke-linecap="round" stroke-linejoin="round"></g><g id="SVGRepo_iconCarrier"> <path fill-rule="evenodd" clip-rule="evenodd" d="M5 4.5C5 2.567 6.567 1 8.5 1C10.433 1 12 2.567 12 4.5V4.55376C12 5.82905 11.3167 7.00657 10.2094 7.63929L10.0156 7.75005L16 11.1697V15.0001H0V11.1697L9.21712 5.9028C9.70123 5.62617 10 5.11134 10 4.55376V4.5C10 3.67157 9.32843 3 8.5 3C7.67157 3 7 3.67157 7 4.5V5H5V4.5ZM8 8.9018L2 12.3304V13.0001H14V12.3304L8 8.9018Z" fill="#333333"></path> </g></svg>
  </div>
</header>

  <div id="fascinationContainer">
    <h1 id="fascination">Your wardrobe just got a <span id="fascinationSpan">personal assistant.</span></h1>
    <div style="display: flex; justify-content: center; gap: 1vw">
      <button class="addItemButton" onclick="openPopup2()">Add New Item</button>
      <button class="addItemButton" onclick="openPopup()">Generate Outfit ✨</button>
      <button class="addItemButton" onclick="window.location.href = ('ToOutfitServlet')">Go To Outfits</button>
    </div>
  </div>
  <div id="outerWaveContainer">
    <div id="waveContainer"></div>
      <svg id="wave" xmlns="http://www.w3.org/2000/svg" xmlns:xlink="http://www.w3.org/1999/xlink" preserveAspectRatio="xMidYMid meet" version="1.0" viewBox="-0.2 -4.4 466.0 60.6" zoomAndPan="magnify" style="fill: rgb(1, 118, 129);" original_string_length="635"><g id="__id0_shh9k08pmz"><path d="M369.54,55.44c-35.04,0-70.06-4.9-104-14.7l-76.93-22.2C132.61,2.39,72.76-0.33,15.53,10.7L2.38,13.23 c-1.08,0.21-2.13-0.5-2.34-1.59c-0.21-1.08,0.5-2.13,1.59-2.34l13.15-2.53c57.85-11.15,118.35-8.4,174.95,7.93l76.93,22.2 c64.03,18.48,131.95,19.36,196.44,2.56c1.07-0.28,2.16,0.36,2.44,1.43s-0.36,2.16-1.43,2.44C433.1,51.41,401.31,55.44,369.54,55.44z" style="fill: #017681;"/></g></svg>

  </div>

  <div id="filterContainer" style="margin-left: 5vw">
    <label for="filterSelect" id="filterLabel" style="font-size: 18px; font-weight: bold; margin-bottom: 8px; display: block;">Filter By</label>

    <div class="custom-select">
      <select id="filterSelect" name="filter">
        <option value="None">None</option>
        <option value="Category - Top">Category - Top</option>
        <option value="Category - Bottom">Category - Bottom</option>
        <option value="Category - Shoes">Category - Shoes</option>
        <option value="In Closet">In Closet</option>
        <option value="To Wash">To Wash</option>
        <option value="Season - Spring">Season - Spring</option>
        <option value="Season - Summer">Season - Summer</option>
        <option value="Season - Autumn">Season - Fall</option>
        <option value="Season - Winter">Season - Winter</option>
        <option value="Season - All Year">Season - All Year</option>
      </select>
    </div>
  </div>

  <div id="outerItemsContainer">
    <div id="innerItemsContainer">
      <%
        List<CapoAbbigliamento> listaCapi = (List<CapoAbbigliamento>) request.getAttribute("listaCapi");

        if(!listaCapi.isEmpty()){
          for(CapoAbbigliamento c: listaCapi){
      %>

        <div class="col-md-3">
          <div class="wsk-cp-product" id="data<%= c.getId() %>">
            <div class="wsk-cp-img">
              <img src="<%=c.getImmagine()%>" alt="Product" class="img-responsive"  height="330px"/>
            </div>
            <div class="wsk-cp-text">
              <div class="category">
                <span style="font-size: 0.5rem;"><%=c.getStato()%></span>
              </div>
              <div class="title-product">
                <h3 style="font-size: 0.8rem; "><%= c.getNome()%></h3>
              </div>
              <div class="description-prod">
                <p style="font-size: 0.8rem;"> <%=c.getDescrizione()%></p>
              </div>
              <div class="card-footer">
                <div class="wcf-left"><span class="price"> <%= c.getStagione()%> </span></div>
                <div class="wcf-right"><a href="#" class="buy-btn"><i class="zmdi zmdi-shopping-basket"></i></a></div>
              </div>
            </div>
          </div>
        </div>


 <%} }
        else{
 %>
      <h4>No items in the wardrobe.</h4>
<%}%>
    </div>
  </div>

  <div id="popupOverlay" class="overlay"></div>
  <div id="popup" class="popup">

    <div id="outfitSelectionContainer">
      <div style="z-index: 2000">
        <button class="closeButton" onclick="closePopup()">✖</button>
      </div>

      <h3>Select your favorite outfit, then go all the way down to save it!</h3>
      <!-- Outfit 1 -->
      <div class="outfitRow">
        <div class="content">
          <div class="categoryRow" id="category1">
            <div class="category" >
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
        <button class="selectButton" style="margin-top: 5vw" id="button1">Select</button>
      </div>

      <!-- Outfit 2 -->
      <div class="outfitRow">
        <div class="content">
          <div class="categoryRow"  id="category2">
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
        <button class="selectButton" style="margin-top: 5vw" id="button2">Select</button>
      </div>

      <!-- Outfit 3 -->
      <div class="outfitRow">
        <div class="content">
          <div class="categoryRow"  id="category3">
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
        <button class="selectButton" style="margin-top: 5vw" id="button3">Select</button>
      </div>

      <!-- Input per Nome e Descrizione -->
      <div class="outfitDetails">
        <input  class="outfitForms" type="text" placeholder="Outfit Name">
        <textarea class="outfitForms" placeholder="Outfit Description"></textarea>
      </div>

      <!-- Pulsante "Salva Outfit" -->
      <button class="saveButton" style="margin-top: 2vw" onclick="saveOutfitAI()">Save Outfit</button>
    </div>
  </div>

<div id="popupOverlay2" class="overlay2"></div>
<div id="popup2" class="popup2">
  <div style="z-index: 2000">
    <button class="closeButton" onclick="closePopup2()">✖</button>
  </div>

  <form id="capoForm" ACTION="addCapoServlet" method="post" enctype="multipart/form-data" onsubmit=" return validateC()">
    <label for="name">Clothing Name:</label>
    <input type="text" id="name" name="name" required>

    <label for="description">Description:</label>
    <textarea id="description" name="description" required></textarea>

    <label for="material">Material:</label>
    <select id="material" name="material" required>
      <option value="Cotton">Cotton</option>
      <option value="Wool">Wool</option>
      <option value="Polyester">Polyester</option>
      <option value="Leather">Leather</option>
      <option value="Silk">Silk</option>
      <option value="Denim">Denim</option>
      <option value="Nylon">Nylon</option>
      <option value="Linen">Linen</option>
      <option value="Fleece">Fleece</option>
      <option value="Velvet">Velvet</option>
      <option value="Spandex">Spandex</option>
      <option value="Suede">Suede</option>
      <option value="Canvas">Canvas</option>
      <option value="Mesh">Mesh</option>
      <option value="Rubber">Rubber</option>
      <option value="Synthetic">Synthetic</option>
      <option value="Knit">Knit</option>
      <option value="Faux Leather">Faux Leather</option>
    </select>

    <label for="color">Color:</label>
    <select id="color" name="color" required>
      <option value="Red">Red</option>
      <option value="Green">Green</option>
      <option value="Blue">Blue</option>
      <option value="Yellow">Yellow</option>
      <option value="Cyan">Cyan</option>
      <option value="Magenta">Magenta</option>
      <option value="Black">Black</option>
      <option value="White">White</option>
      <option value="Gray">Gray</option>
      <option value="Orange">Orange</option>
      <option value="Purple">Purple</option>
      <option value="Brown">Brown</option>
      <option value="Pink">Pink</option>
      <option value="Turquoise">Turquoise</option>
      <option value="Beige">Beige</option>
      <option value="Lavender">Lavender</option>
      <option value="Maroon">Maroon</option>
      <option value="Olive">Olive</option>
      <option value="Navy">Navy</option>
      <option value="Teal">Teal</option>
      <option value="Silver">Silver</option>
      <option value="Gold">Gold</option>
      <option value="Indigo">Indigo</option>
      <option value="Coral">Coral</option>
      <option value="Salmon">Salmon</option>
      <option value="Chocolate">Chocolate</option>
      <option value="Peru">Peru</option>
      <option value="Crimson">Crimson</option>
      <option value="Sky Blue">Sky Blue</option>
      <option value="Lime">Lime</option>
    </select>

    <label for="style">Style:</label>
    <select id="style" name="style" required>
      <option value="Casual">Casual</option>
      <option value="Elegant">Elegant</option>
      <option value="Sporty">Sporty</option>
    </select>

    <label for="season">Season:</label>
    <select id="season" name="season" required>
      <option value="Summer">Summer</option>
      <option value="Winter">Winter</option>
      <option value="Spring">Spring</option>
      <option value="Fall">Fall</option>
    </select>

    <label for="image">Upload an Image:</label>
    <input type="file" id="image" name="image" accept="image/*" required>

    <label for="category">Category:</label>
    <select id="category" name="category" required>
      <option value="Hoodie">Hoodie</option>
      <option value="Blazer">Blazer</option>
      <option value="T-shirt">T-Shirt</option>
      <option value="Sweater">Sweater</option>
      <option value="Jacket">Jacket</option>
      <option value="Coat">Coat</option>
      <option value="Vest">Vest</option>
      <option value="Cardigan">Cardigan</option>
      <option value="Shirt">Shirt</option>
      <option value="Tank Top">Tank Top</option>
      <option value="Jeans">Jeans</option>
      <option value="Shorts">Shorts</option>
      <option value="Trousers">Trousers</option>
      <option value="Sweatpants">Sweatpants</option>
      <option value="Leggings">Leggings</option>
      <option value="Skirt">Skirt</option>
      <option value="Cargo Pants">Cargo Pants</option>
      <option value="Chinos">Chinos</option>
      <option value="Joggers">Joggers</option>
      <option value="Culottes">Culottes</option>
      <option value="Sneakers">Sneakers</option>
      <option value="Boots">Boots</option>
      <option value="Loafers">Loafers</option>
      <option value="Heels">Heels</option>
      <option value="Sandals">Sandals</option>
      <option value="Flats">Flats</option>
      <option value="Running Shoes">Running Shoes</option>
      <option value="Oxfords">Oxfords</option>
      <option value="Espadrilles">Espadrilles</option>
    </select>

    <label for="bodyPart">Body Part:</label>
    <select id="bodyPart" name="bodyPart" required>
      <option value="top">Top</option>
      <option value="bottom">Bottom</option>
      <option value="shoes">Shoes</option>
    </select>

    <button type="submit" class="bottoneSubmit">Add Clothing Item</button>
  </form>
</div>



<div id="overlay3" class="overlay-new"></div>
<div id="popupContainer3" class="popup-new">
  <div style="z-index: 2000">
    <button class="close-btn" onclick="closePopup3()">✖</button>
  </div>

  <form id="clothingForm" action="editCapoServlet" method="post" enctype="multipart/form-data" onsubmit=" return onEditFormSubmit()">
    <div class="form-row-new">
      <div class="form-group-new">
        <label for="clothingName2">Clothing Name:</label>
        <input type="text" id="clothingName2" name="name" required>
      </div>
      <div class="form-group-new">
        <label for="description2">Description:</label>
        <textarea id="description2" name="description" required></textarea>
      </div>
    </div>

    <div class="form-row-new">
      <div class="form-group-new">
        <label for="material2">Material:</label>
        <select id="material2" name="material" required>
          <option value="Cotton">Cotton</option>
          <option value="Wool">Wool</option>
          <option value="Polyester">Polyester</option>
          <option value="Leather">Leather</option>
          <option value="Silk">Silk</option>
          <option value="Denim">Denim</option>
          <option value="Nylon">Nylon</option>
          <option value="Linen">Linen</option>
          <option value="Fleece">Fleece</option>
          <option value="Velvet">Velvet</option>
          <option value="Spandex">Spandex</option>
          <option value="Suede">Suede</option>
          <option value="Canvas">Canvas</option>
          <option value="Mesh">Mesh</option>
          <option value="Rubber">Rubber</option>
          <option value="Synthetic">Synthetic</option>
          <option value="Knit">Knit</option>
          <option value="Faux Leather">Faux Leather</option>
        </select>
      </div>
      <div class="form-group-new">
        <label for="color2">Color:</label>
        <select id="color2" name="color" required>
          <option value="-">-</option>
          <option value="Red">Red</option>
          <option value="Green">Green</option>
          <option value="Blue">Blue</option>
          <option value="Yellow">Yellow</option>
          <option value="Cyan">Cyan</option>
          <option value="Magenta">Magenta</option>
          <option value="Black">Black</option>
          <option value="White">White</option>
          <option value="Gray">Gray</option>
          <option value="Orange">Orange</option>
          <option value="Purple">Purple</option>
          <option value="Brown">Brown</option>
          <option value="Pink">Pink</option>
          <option value="Turquoise">Turquoise</option>
          <option value="Beige">Beige</option>
          <option value="Lavender">Lavender</option>
          <option value="Maroon">Maroon</option>
          <option value="Olive">Olive</option>
          <option value="Navy">Navy</option>
          <option value="Teal">Teal</option>
          <option value="Silver">Silver</option>
          <option value="Gold">Gold</option>
          <option value="Indigo">Indigo</option>
          <option value="Coral">Coral</option>
          <option value="Salmon">Salmon</option>
          <option value="Chocolate">Chocolate</option>
          <option value="Peru">Peru</option>
          <option value="Crimson">Crimson</option>
          <option value="Sky Blue">Sky Blue</option>
          <option value="Lime">Lime</option>
        </select>
      </div>
    </div>

    <div class="form-row-new">
      <div class="form-group-new">
        <label for="style2">Style:</label>
        <select id="style2" name="style" required>
          <option value="-">-</option>
          <option value="Casual">Casual</option>
          <option value="Elegant">Elegant</option>
          <option value="Sporty">Sporty</option>
        </select>
      </div>
      <div class="form-group-new">
        <label for="season2">Season:</label>
        <select id="season2" name="season" required>
          <option value="Summer">Summer</option>
          <option value="Winter">Winter</option>
          <option value="Spring">Spring</option>
          <option value="Fall">Fall</option>
        </select>
      </div>
    </div>

    <div class="form-row-new">
      <div class="form-group-new">
        <label for="imageUpload">Upload an Image:</label>
        <input type="file" id="imageUpload" name="image" accept="image/*" >
      </div>
      <div class="form-group-new">
        <label for="category4">Category:</label>
        <select id="category4" name="category" required>
          <option value="-">-</option>
          <option value="Hoodie">Hoodie</option>
          <option value="Blazer">Blazer</option>
          <option value="T-shirt">T-Shirt</option>
          <option value="Sweater">Sweater</option>
          <option value="Jacket">Jacket</option>
          <option value="Coat">Coat</option>
          <option value="Vest">Vest</option>
          <option value="Cardigan">Cardigan</option>
          <option value="Shirt">Shirt</option>
          <option value="Tank Top">Tank Top</option>
          <option value="Jeans">Jeans</option>
          <option value="Shorts">Shorts</option>
          <option value="Trousers">Trousers</option>
          <option value="Sweatpants">Sweatpants</option>
          <option value="Leggings">Leggings</option>
          <option value="Skirt">Skirt</option>
          <option value="Cargo Pants">Cargo Pants</option>
          <option value="Chinos">Chinos</option>
          <option value="Joggers">Joggers</option>
          <option value="Culottes">Culottes</option>
          <option value="Sneakers">Sneakers</option>
          <option value="Boots">Boots</option>
          <option value="Loafers">Loafers</option>
          <option value="Heels">Heels</option>
          <option value="Sandals">Sandals</option>
          <option value="Flats">Flats</option>
          <option value="Running Shoes">Running Shoes</option>
          <option value="Oxfords">Oxfords</option>
          <option value="Espadrilles">Espadrilles</option>
        </select>
      </div>
    </div>

    <div class="form-row-new">
      <div class="form-group-new">
        <label for="bodyPart2">Body Part:</label>
        <select id="bodyPart2" name="bodyPart" required>
          <option value="-">-</option>
          <option value="top">Top</option>
          <option value="bottom">Bottom</option>
          <option value="shoes">Shoes</option>
        </select>
      </div>
      <div class="form-group-new">
        <label for="state2">State:</label>
        <select id="state2" name="state" required>
          <option value="To Wash">To Wash</option>
          <option value="In Closet">In Closet</option>
        </select>
      </div>
    </div>

    <input type="hidden" value="" name="idCapo" id="hiddenParameter">
    <input type="hidden" value="" name="flag" id="flagParameter">

    <button type="submit" class="submit-btn-new">Edit Clothing Item</button>

  </form>

  <button type="submit" class="submit-btn-new" onclick="deleteItem()">Delete Item</button>

</div>


<div style="display: none;" id="loadingOverlay">
  <div class="spinner"></div>
  <h3>We're creating your outfits...</h3>
</div>



  <script src="${pageContext.request.contextPath}/front-end/js/wardrobe.js"></script>
</body>
</html>
