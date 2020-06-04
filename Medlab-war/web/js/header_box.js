/* 
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
var show_filter_box = false;
document.getElementById("search_box").style.display = "none";
function show_hide_filter_box() {
    if (show_filter_box) {
        hide("search_box");
        show_filter_box = false;
        document.getElementById("search_box_icon").className ="fa fa-search";
   } else {
       show("search_box");
       show_filter_box = true;
   }   
}

var show_action_box = false;
document.getElementById("action_box").style.display = "none";

function show_hide_action_box(){
    if (show_action_box) {
        hide("action_box");
        show_action_box = false;
        document.getElementById("action_box_icon").className ="fa fa-plus";
   } else {
       show("action_box");
       show_action_box = true;
   }  
}

function show(box) {
    document.getElementById(box).style.display = "block"; 
    document.getElementById(box + "_icon").className ="fa fa-times";
}

function hide(box) {
    document.getElementById(box).style.display = "none";
}

function loadLastSortSelection(type){
    if ((window.location.href.indexOf("Search") > -1) || (!sessionStorage.getItem("last_sort_selected_"+type))) sessionStorage.setItem("last_sort_selected_"+type,"recent");
    document.getElementById("sort_type_"+type).value = sessionStorage.getItem("last_sort_selected_"+type);
}

function sortList(command, type){
    window.location.href = "FrontController?command=Show"+command+"Command&sort="+document.getElementById("sort_type_"+type).value;
    sessionStorage.setItem("last_sort_selected_"+type, document.getElementById("sort_type_"+type).value);
}

function showAll(command, type){
    window.location.href = "FrontController?command=Show"+command+"Command&sort="+ sessionStorage.getItem("last_sort_selected_"+type);  
}

function setSpeciality(speciality) {
    document.getElementById("speciality").value = speciality;
}


function setNewPaginationSearch() {
    var entity = document.getElementById("entity").value;
    var page_number = document.getElementById("page_number").value;
    window.location.href = "FrontController?command=ShowPaginationCommand&entity="+entity+"&page_number="+page_number;  
}

function setNewEntitySearch() {
    var entity = document.getElementById("entity").value;
    window.location.href = "FrontController?command=ShowPaginationCommand&entity="+entity;   
}