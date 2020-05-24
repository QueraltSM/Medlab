<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <meta name='viewport' content='width=device-width, initial-scale=1'>
    <title>Medlab | Create news</title>
    <link rel="stylesheet" type="text/css" href="css/menu.css">
    <link rel="stylesheet" type="text/css" href="css/main.css">
    <link rel="stylesheet" type="text/css" href="css/form.css">
    <script src='https://kit.fontawesome.com/a076d05399.js'></script>
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/4.7.0/css/font-awesome.min.css">
    <link href='http://fonts.googleapis.com/css?family=Montserrat:400,700' rel='stylesheet' type='text/css'>
    <link rel="icon" href="Images/icon.jpg">
</head>
<body>
    <jsp:include page="header.jsp" /> 
    <div class ="header">
        <center><p class="header_title">Create news</p></center>
    </div>
    <div class ="patient_data">
      <form action="FrontController" class="signup-form">
      <div class="form-body">
          <div class="form-group">
          <label for="title" class="label-title">Title</label>
          <input type="text" id="title" name="title" class="form-input" required>
        </div>
          <div class="form-group">
          <label for="description" class="label-title">Description</label>
          <textarea class="form-input" id="description" name="description" rows="10" cols="50" style="height:auto" required></textarea>
        </div>
        <div class="horizontal-group">
            <div class="form-group left">
            <label class="label-title">Speciality</label>
            <select class="form-input" name="speciality" id="speciality">
                      <option value="Allergy_and_Inmunology">Allergy & Inmunology</option>
                      <option value="Anesthesiology">Anesthesiology</option>
                      <option value="Dermatology">Dermatology</option>
                      <option value="Diagnostic_Radiology">Diagnostic Radiology</option>
                      <option value="Emergency_Medicine">Emergency Medicine</option>
                      <option value="Family_Medicine">Family Medicine</option>
                      <option value="Internal_Medicine">Internal Medicine</option>
                      <option value="Medical_Genetics">Medical Genetics</option>
                      <option value="Neurology">Neurology</option>
                      <option value="Nuclear_Medicine">Nuclear Medicine</option>
                      <option value="Obstretics_and_Gynecology">Obstretics and Gynecology</option>
                      <option value="Opthalmology">Opthalmology</option>
                      <option value="Pathology">Pathology</option>
                      <option value="Pediatrics">Pediatrics</option>
                      <option value="Preventive_Medicine">Preventive Medicine</option>
                      <option value="Psychiatry">Psychiatry</option>
                      <option value="Radiation_Oncology">Radiation Oncology</option>
                      <option value="Surgery">Surgery</option>
                      <option value="Urology">Urology</option>
            </select>
          </div>
        </div>
        <div class="form-footer">
            <input name="command" type="hidden" value="AddNewsCommand">
            <button class="btn">Share</button>			
        </div>
        </div> 
    </form>
    </div>
    <jsp:include page="footer.jsp" /> 
</body>
</html>
