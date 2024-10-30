
function escapeXHTML(str)
{
	return str.replace(/[&]/g, "&amp;")
			  .replace(/[<]/g, "&lt;")
			  .replace(/[>]/g, "&gt;")
			  ;
}

function escapeXHTMLAttribute(str)
{
	return str.replace(/[\"]/g, "&quot;");
}


function innerXHTMLAttributes(el, ignore)
{
	// Start with an empty attribute list
	var str = '';

	// Output attributes for the element
	for (var i = 0; i < el.attributes.length; i++)
	{
		// Get this attribute
		var attr = el.attributes[i];

		// Only output if it has a value of type string
		if (attr.nodeValue && typeof(attr.nodeValue) == "string")
		{
			// and it's not one we want to ignore
			if (!ignore || attr.nodeName.toLowerCase().search(ignore) == -1)
			{
				// Output the attribute (space separated if necessary)
				if (str.length) str += ' ';
				str += attr.nodeName.toLowerCase();
				str += '="' + escapeXHTMLAttribute(attr.nodeValue) + '"';
			}
		}
	}

	// Return the resulting attribute string
	return str;
}


function innerXHTML(el, ignore)
{
	var str = '';

	var r2; var r = document.body.createTextRange();
	r.moveToElementText(el);

	for (var i = 0; i < el.children.length; i++)
	{
		r2 = document.body.createTextRange();
		r2.moveToElementText(el.children[i]);

		r.setEndPoint("EndToStart", r2);
		str += escapeXHTML(r.text);

		str += outerXHTML(el.children[i], ignore);

		r.moveToElementText(el);
		r.setEndPoint("StartToEnd", r2);
	}

	return str + escapeXHTML(r.text);
}


function outerXHTML(el, ignore)
{
	var attrs = innerXHTMLAttributes(el, ignore);

	var inner = innerXHTML(el, ignore);

	return '<' + el.nodeName.toLowerCase()
			+ (attrs.length ? ' ' + attrs : '')
			+ (inner.length ? '>' + inner + '</' + el.nodeName.toLowerCase() + '>'
							: ' />');
}
