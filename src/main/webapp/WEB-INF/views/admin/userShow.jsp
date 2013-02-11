<%@ include file="/templates/header.jsp"%>

		<div id="content">
			<div id="column1">
				<div class="headerbox">
					<h2 class="headertext">Käyttäjätiedot</h2>
				</div>
				<div class="chartbox">
					<div class="challenge">
					
					<table>
					<tr>
							<td>
									<p><spring:message code="user.username" text="User name:" /></p>
								</td>

							<td><p>${user.getUsername()}</p></td>
						</tr>
						<tr>
							<td>
									<p><spring:message code="user.firstName" text="First name:" /></p>
								</td>
							<td><p>${user.getFirstName()}</p></td>
						</tr>
						<tr>
							<td>
									<p><spring:message code="user.lastName" text="Last name:" /></p>
								</td>
							<td><p>${user.getLastName()}</p></td>
						</tr>
						<tr>
							<td>
							<p><spring:message code="user.accountType" text="Account type:" /></p>
									</td>
									<td><p><spring:message code="user.${user.getAuthority()}" text="Unknown" /></p></td>
						</tr>
						<tr>
							<td>
							<p><spring:message code="user.accountStatus" text="Account status:" /></p>
									</td>
									<td><p><spring:message code="user.active.${user.isEnabled()}" text="Unknown" /></p></td>
						</tr>
						
					</table>
					
					<a class="nappi" style="float:left;" href="/uftc/user/edit?userId=${loggedInUser.getId()}">Muokkaa</a>
					<a class="nappi" style="float:right;" href="/uftc/admin">Takaisin</a>
					

						</div>


					</div>
				</div>
			</div>
			<!--End of column1-->
			
			<div id="column2">
				<div class="headerbox">
					<h2 class="headertext">TAPAHTUMAT</h2>
					</div>
					<img class="headerlogo" src="/uftc/pics/runner.jpg">
					<div class="chartbox">
				<div class="tapahtumat">
					<div class="single">
						<div class="date">
							<p>23.7.2012</p>
						</div>
						<div class="action">
							<p>Janne teki 20 punnerrusta</p>
						</div>
						<div class="like">
							<a href="#"><img src="/uftc/pics/like.jpeg"
								style="width: 15%;"></a>
						</div>
					</div>
					<div class="single">
						<div class="date">
							<p>23.7.2012</p>
						</div>
						<div class="action">
							<p>Janne teki 20 punnerrusta</p>
						</div>
						<div class="like">
							<a href="#"><img src="/uftc/pics/like.jpeg"
								style="width: 15%;"></a>
						</div>
					</div>
					<div class="single">
						<div class="date">
							<p>23.7.2012</p>
						</div>
						<div class="action">
							<p>Janne teki 20 punnerrusta</p>
						</div>
						<div class="like">
							<a href="#"><img src="/uftc/pics/like.jpeg"
								style="width: 15%;"></a>
						</div>
					</div>
					<div class="single">
						<div class="date">
							<p>23.7.2012</p>
						</div>
						<div class="action">
							<p>Janne teki 20 punnerrusta</p>
						</div>
						<div class="like">
							<a href="#"><img src="/uftc/pics/like.jpeg"
								style="width: 15%;"></a>
						</div> 
					</div>
					</div>

				</div>

			</div>
			</div>
			
			
			<!--End of column2-->
		<%-- </div> --%>
		<!--End of content-->

		<%@ include file="/templates/footer.jsp"%>